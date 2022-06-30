package para.sso.ticket.registry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.util.Assert;
import para.sso.authentication.Authentication;
import para.sso.configuration.CasConfigurationProperties;
import para.sso.ticket.SimpleTicketGrantingTicket;
import para.sso.ticket.Ticket;
import para.sso.ticket.TicketGrantingTicket;
import para.sso.ticket.TicketGrantingTicketImpl;
import para.sso.ticket.accesstoken.AccessToken;
import para.sso.ticket.conversion.ITicketConversionHandler;
import para.sso.ticket.conversion.TicketConversionManager;
import para.sso.ticket.refreshtoken.RefreshToken;
import static para.sso.ticket.registry.MultiLayerCacheTicketRegistry.TICKET_V2;
import para.sso.ticket.util.TicketUtils;
import para.sso.web.support.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Key-value ticket registry implementation that stores tickets in redis keyed on the ticket ID.
 *
 * @author serv
 * @since 5.1.0
 */
@SuppressWarnings("unchecked")
public class RedisTicketRegistry extends AbstractTicketRegistry {

    public static final String TGT_USER_MAPPING_PREFIX = "TGT-USER:";
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTicketRegistry.class);

    private static final String SSO_TICKET_PREFIX = "SSO_TICKET:";
    private static final String CAS_TICKET_PREFIX = "CAS_TICKET:";
    private static final String TGT_TICKET_PREFIX = "CAS_TICKET:TGT";
    private static final String NO_REDIS_CLIENT_IS_DEFINED = "No redis client is defined.";
    private static final String TICKET_MAPPING_PREFIX = "TICKET_MAPPING:";
    private static final String SESSION_MGR_PREFIX = "SESSION_MGR_PREFIX:";

    private static final String TGT_PARRTN = "CAS_TICKET:TGT*";

    @NotNull
    private final TicketRedisTemplate client;

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private TicketConversionManager ticketConversionManager;

    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public RedisTicketRegistry(final TicketRedisTemplate client) {
        this.client = client;
    }

    public RedisTicketRegistry(final TicketRedisTemplate client, CasConfigurationProperties casProperties) {
        this.client = client;
        this.casProperties = casProperties;
    }

    @Override
    public boolean deleteSingleTicket(final String ticketId) {
        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);
        try {
            final String redisKey = getTicketRedisKey(ticketId, isTicketNewVersion(ticketId));
            if (ticketId.startsWith("TGT-")) {
                Ticket ticket = getTicket(ticketId);
                if(ticket != null){
                    TicketGrantingTicketImpl ticketGrantTicketImpl = (TicketGrantingTicketImpl) ticket;
                    String username = ticketGrantTicketImpl.getAuthentication().getPrincipal().getId();
                    final Set<Object> redisKeys = this.client.opsForSet().members(
                            getTgtUserMappingKey(username));
                    redisKeys.forEach(key -> {
                        String redisKeyInMap = (String) key;
                        if(redisKeyInMap.contains(ticketId)){
                            this.client.opsForSet().remove(getTgtUserMappingKey(username), key);
                        }
                    });
                }
            }
            this.client.delete(redisKey);
            return true;
        } catch (final Exception e) {
            LOGGER.error("Ticket not found or is already removed. Failed deleting [{}]", ticketId, e);
        }
        return false;
    }

    private String getTgtUserMappingKey(String username) {
        return TGT_USER_MAPPING_PREFIX + encodeTicketId(username);
    }

    // Add a prefix as the key of redis
    private static String getTicketRedisKey(final String ticketId, boolean isNewVersion) {
        /*String prefix = (isNewVersion && !ticketId.startsWith(OCU_TICKET_PREFIX) ? SSO_TICKET_PREFIX : CAS_TICKET_PREFIX);
        return prefix + ticketId;*/
        return (isNewVersion ? SSO_TICKET_PREFIX : CAS_TICKET_PREFIX) + ticketId;
    }

    @Override
    public void addTicket(Ticket ticket) {


        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);
        try {
            String username = "";
            boolean isAlreadySimple = casProperties.getAuthn().isTurnOnMultiLayerCache();
            if(!isAlreadySimple) {
                ITicketConversionHandler ticketConversionHandler = ticketConversionManager
                        .getHandler(ticket.getPrefix());
                Ticket simpleTicket = null;
                if (ticketConversionHandler != null && !ticket.getId().startsWith("ey")) {
                    simpleTicket = ticketConversionHandler.convertToSimple(ticket);
                }
                ticket = simpleTicket != null ? simpleTicket : ticket;
            }else if (ticket.getPrefix().equals(TicketGrantingTicket.PREFIX)) {
                SimpleTicketGrantingTicket simpleTicketGrantingTicket = (SimpleTicketGrantingTicket) ticket;
                username = simpleTicketGrantingTicket.getUser();
            }
            LOGGER.debug("Adding ticket [{}]", ticket);
            boolean shouldHandleNewVersion = false;
            if (!ticket.getId().startsWith("ey") && (TicketGrantingTicket.PREFIX.equals(ticket.getPrefix())
                    || AccessToken.PREFIX.equals(ticket.getPrefix()) || RefreshToken.PREFIX
                    .equals(ticket.getPrefix()))) {
                shouldHandleNewVersion = true;
            }
            final String redisKey = RedisTicketRegistry.getTicketRedisKey(ticket.getId(), shouldHandleNewVersion);
            // Encode first, then add
            final Ticket encodeTicket = this.encodeTicket(ticket);

            if(shouldHandleNewVersion) {
                String ticketString = TicketUtils.objectToString(encodeTicket);
                this.client.boundValueOps(redisKey)
                        .set(ticketString, getTimeout(ticket), TimeUnit.SECONDS);
                //            addTGTMappings(ticket);
            } else {
                this.client.boundValueOps(redisKey).set(encodeTicket, getTimeout(ticket), TimeUnit.SECONDS);
            }

            addTGTMappings(ticket.getId(), username,request);

            /*
             * if(ticket instanceof TicketGrantingTicket){ TicketGrantingTicket tgt =
             * (TicketGrantingTicket)ticket; UserSsoSession session = new UserSsoSession();
             * session.setTgtId(tgt.getId());
             * session.setUsername(tgt.getAuthentication().getPrincipal().getId());
             * session.setCreateTime(new Date()); this.entityManager.persist(session); }
             */

        } catch (final Exception e) {
            LOGGER.error("Failed to add [{}]", e);
        }
    }
    public void addTGTMappings(String ticketId, String username,HttpServletRequest request) {
        if (ticketId.startsWith("TGT-")) {
            String deviceType = StringUtils.defaultIfBlank(request.getParameter("device_type"), "unknown");
            this.client.opsForSet().add(getTgtUserMappingKey(username), deviceType + ":" +ticketId);
            //rest调用mfa接口  改在postlogin接口存入level
            //                TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
            //                TgtMappingTicket mappingTgt = new TgtMappingTicket(tgt.getAuthentication()
            //                .getPrincipal().getId(), "1" , true);
            //                this.client.boundValueOps(TgtMappingUtils.getMappingTgtId(ticket.getId())).set
            //                (mappingTgt, ticket.getExpirationPolicy().getTimeToLive().intValue(), TimeUnit
            //                .SECONDS);
        }
    }

    @Override
    public Ticket getTicket(final String ticketId) {
        return getTicket(ticketId, isTicketNewVersion(ticketId));
    }

    public Ticket getTicket(final String ticketId, boolean isNewVersion) {
        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);
        if (StringUtils.isEmpty(ticketId)) {
            return null;
        }
        try {
            final String redisKey = RedisTicketRegistry.getTicketRedisKey(ticketId, isNewVersion);
            Ticket ticket;
            if (isNewVersion) {
                String ticketString = (String) this.client.boundValueOps(redisKey).get();
                if(ticketString == null) {
                    return null;
                }
                ticket = TicketUtils.buildMessageFromJSON(ticketId, ticketString);
                boolean needSimple = casProperties.getAuthn().isTurnOnMultiLayerCache();
                if (!needSimple) {
                    ITicketConversionHandler ticketConversionHandler = ticketConversionManager
                            .getHandler(ticket.getPrefix());
                    if (ticketConversionHandler != null) {
                        ticket = ticketConversionHandler.convertSimpleToOriginal(ticket, ticketId, this);
                    }
                }
            } else {
                ticket = (Ticket) this.client.boundValueOps(redisKey).get();
            }
            if (ticket != null) {
                // Decoding add first
                return decodeTicket(ticket);
            }
        } catch (final Exception e) {
            LOGGER.error("Failed fetching [{}] ", ticketId, e);
        }
        return null;
    }

    @Override
    public long deleteAll() {
        //        final Set<String> redisKeys = this.client.keys(getPatternTicketRedisKey(false));
        //        Set<String> redisKeys2 = this.client.keys(getPatternTicketRedisKey(true));
        //        final int size = redisKeys.size();
        //        this.client.delete(redisKeys);
        //
        //        int size2 = redisKeys2.size();
        //        this.client.delete(redisKeys2);
        LOGGER.error("The method is not supported : deleteAll");
        return 0;
    }

    @Override
    public long deleteAll(Set<String> tickets) {
        this.client.delete(tickets);
        return tickets.size();
    }

    @Override
    public Collection<Ticket> getTickets() {
        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);

        final Set<Ticket> tickets = new HashSet<>();
//        final Set<String> redisKeys = this.client.keys(RedisTicketRegistry.getPatternTicketRedisKey(false));
//        redisKeys.forEach(redisKey -> {
//            final Ticket ticket = (Ticket) this.client.boundValueOps(redisKey).get();
//            if (ticket == null) {
//                this.client.delete(redisKey);
//            } else {
//                // Decoding add first
//                tickets.add(this.decodeTicket(ticket));
//            }
//        });
        LOGGER.error("The method is not supported : getTickets");
        return tickets;
    }


    public void deleteIncludeKeyTickets(final String ticketId) {
        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);
        if (StringUtils.isEmpty(ticketId)) {
            return;
        }

        Set<String> keys = this.client.keys("*" + ticketId + "*");
        if (CollectionUtils.isNotEmpty(keys)) {
            keys.forEach(key -> {
                try {
                    this.client.delete(key);
                } catch (Exception e) {
                    LOGGER.error("Delete include key ticket failure, ticket: {}", ticketId);
                }
            });
        }
    }

    @Override
    public Ticket updateTicket(final Ticket ticket) {
        return updateTicket(ticket, isTicketNewVersion(ticket.getId()));
    }

    @Override
    public Ticket getTicketToReUse(Authentication authentication) {
        List<Ticket> ticketGrantingTickets = getTicketsByUsername(authentication.getPrincipal().getId());

        Ticket tgt = ticketGrantingTickets.stream()
                .filter(t -> t.getCreationTime().plus(1, ChronoUnit.DAYS).isAfter(
                        ZonedDateTime.now())).findFirst()
                .orElse(null);
        return tgt;
    }

    @Override
    public List<Ticket> getTicketsByUsername(String username) {
        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);
        if (StringUtils.isEmpty(username)) {
            return new ArrayList<>();
        }

        final List<Ticket> tickets = new ArrayList<>();
        String deviceType = StringUtils.defaultIfBlank(request.getParameter("device_type"), "unknown");
        final Set<Object> redisKeys = this.client.opsForSet().members(getTgtUserMappingKey(username));
        redisKeys.forEach(key -> {
            String redisKey = (String) key;
            String deviceTypeInKey = "";

            String[] strArray = redisKey.split(":");
            if (strArray != null && strArray.length >= 2) {
                deviceTypeInKey = strArray[0];
                redisKey = strArray[1];
            }
            if(deviceType.equals(deviceTypeInKey)) {
                String ticketString = (String) this.client
                        .boundValueOps(getTicketRedisKey(redisKey, isTicketNewVersion(redisKey))).get();
                if (ticketString == null) {
                    this.client.delete(redisKey);
                } else {
                    try {
                        Ticket ticket = TicketUtils.buildMessageFromJSON(redisKey, ticketString);

                        if (isTicketNewVersion(redisKey)) {
                            ITicketConversionHandler ticketConversionHandler = ticketConversionManager
                                    .getHandler(ticket.getPrefix());
                            if (ticketConversionHandler != null) {
                                ticket = ticketConversionHandler.convertSimpleToOriginal(ticket, redisKey, this);
                            }
                        }
                        // Decoding add first
                        tickets.add(this.decodeTicket(ticket));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return tickets;
    }

    public Ticket updateTicket(Ticket ticket, boolean isNewVersion) {
        Assert.notNull(this.client, NO_REDIS_CLIENT_IS_DEFINED);
        try {
            LOGGER.debug("Updating ticket [{}]", ticket);
            Ticket encodeTicket;
            final String redisKey = RedisTicketRegistry.getTicketRedisKey(ticket.getId(), isNewVersion);
            if (isNewVersion) {
                boolean isAlreadySimple = casProperties.getAuthn().isTurnOnMultiLayerCache();
                if(!isAlreadySimple) {
                    ITicketConversionHandler ticketConversionHandler = ticketConversionManager
                            .getHandler(ticket.getPrefix());
                    if (ticketConversionHandler != null) {
                        ticket = ticketConversionHandler.convertToSimple(ticket);
                    }
                }
                encodeTicket = this.encodeTicket(ticket);
                String ticketString = TicketUtils.objectToString(encodeTicket);
                this.client.boundValueOps(redisKey).set(ticketString, getTimeout(ticket), TimeUnit.SECONDS);
            } else {
                encodeTicket = this.encodeTicket(ticket);
                this.client.boundValueOps(redisKey).set(encodeTicket, getTimeout(ticket), TimeUnit.SECONDS);
            }
            if (ticket.getId().startsWith("TGT-")) {
                Object msgMap = this.client.boundValueOps(SESSION_MGR_PREFIX + encodeTicket.getId()).get();
                if (msgMap != null) {
                    this.client.boundValueOps(SESSION_MGR_PREFIX + encodeTicket.getId())
                            .set(msgMap, getTimeout(ticket), TimeUnit.SECONDS);
                }
            }
            return encodeTicket;
        } catch (final Exception e) {
            LOGGER.error("Failed to update [{}]", ticket);
        }
        return null;
    }

    private static String getPatternTGTRedisKey(boolean isNewVersion) {
        return (isNewVersion ? SSO_TICKET_PREFIX : CAS_TICKET_PREFIX) + "TGT-*";
    }

    // pattern all ticket redisKey
    private static String getPatternTicketRedisKey(boolean isNewVersion) {
        return (isNewVersion ? SSO_TICKET_PREFIX : CAS_TICKET_PREFIX) + "*";
    }

    /**
     * If not time out value is specified, expire the ticket immediately.
     *
     * @param ticket the ticket
     * @return timeout
     */
    private static int getTimeout(final Ticket ticket) {
        int ttl;
        if(TicketGrantingTicket.PREFIX.equals(ticket.getPrefix())) {
            ttl = ticket.getExpirationPolicy().getTimeToIdle().intValue();
        } else {
            ttl = ticket.getExpirationPolicy().getTimeToLive().intValue();
        }
        if (ttl == 0) {
            return 1;
        }
        return ttl;
    }

    private boolean isTicketNewVersion(String ticketId) {
        if(ticketId.startsWith(TicketGrantingTicket.PREFIX) || ticketId.startsWith(AccessToken.PREFIX) || ticketId.startsWith(RefreshToken.PREFIX) ) {
            String ticketVersion = null;
            String[] strArray = ticketId.split("-");
            if (strArray != null && strArray.length > 1) {
                ticketVersion = strArray[1];
            }
            return TICKET_V2.equals(ticketVersion);
        } /*else if(!ticketId.startsWith("ey")){
            return true;
        }*/
        return false;
    }

    @Override
    public Stream<? extends Ticket> getTicketsStream(Stream<String> keysStream) {
        return keysStream
                .map(redisKey -> {
                    String ticketId = redisKey.replace(SSO_TICKET_PREFIX, "");
                    Ticket ticket = getTicket(ticketId);
                    return ticket;
                })
                .filter(Objects::nonNull)
                .map(this::decodeTicket);

    }
    /**
     * Get a stream of all CAS-related keys from Redis DB.
     *
     * @return stream of all CAS-related keys from Redis DB
     */
    @Override
    public Stream<String> getKeysStream(Cursor<byte[]> cursor) {

        long startTime2 = System.currentTimeMillis();
        Set<String> keys = new HashSet<>();
        while(cursor.hasNext() && keys.size() < casProperties.getTicket().getScanCount()) {
            keys.add(new String(cursor.next()));
        }
        Stream<String> stream = keys.stream();
        LOGGER.info("===================================================get Stream spend time [{}ms]===================================================",System.currentTimeMillis()-startTime2);
        return stream;
    }

    @Override
    public Cursor<byte[]> getScanCursor() {
        long startTime1 = System.currentTimeMillis();
        Cursor<byte[]> cursor = client.getConnectionFactory().getConnection()
                .scan(ScanOptions.scanOptions().match("SSO_TICKET:TGT-*").count(casProperties.getTicket().getScanCount())
                        .build());
        LOGGER.info("-===================================================scan spend time [{}ms]=================================================",System.currentTimeMillis()-startTime1);
        return cursor;
    }

    @Override
    public void removeCleanListValue(String value) {
        this.client.boundListOps("tgt_clean").remove(0,value);
    }

    @Override
    public List<Object> getCleanList(Long i) {
        return this.client.boundListOps("test_clean").range(0,i);
    }

    @Override
    public void addToCleanList(String value) {
        this.client.boundListOps("tgt_clean").rightPush(value);
    }

}
