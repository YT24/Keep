package para.sso.ticket.registry;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import para.sso.authentication.Authentication;
import para.sso.logout.LogoutManager;
import para.sso.ticket.Ticket;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class CachingTicketRegistry extends AbstractMapBasedTicketRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachingTicketRegistry.class);

    private static final int INITIAL_CACHE_SIZE = 50;

    private final Map<String, Ticket> mapInstance;

    private final LoadingCache<String, Ticket> storage;

    private final LogoutManager logoutManager;

    public CachingTicketRegistry(final LogoutManager logoutManager, long maxSize) {
        this.storage = Caffeine.newBuilder()
                .initialCapacity(INITIAL_CACHE_SIZE)
                .maximumSize(maxSize)
                // TODO 设置本地缓存过期时间
                .expireAfter(new CachedTicketExpirationPolicy())
                .build(s -> {
                    LOGGER.error("Load operation of the cache is not supported.");
                    return null;
                });
        this.mapInstance = this.storage.asMap();
        //        this.mapInstance = new HashMap<>();
        this.logoutManager = logoutManager;
    }

    @Override
    public Map<String, Ticket> getMapInstance() {
        return mapInstance;
    }

    public List<Ticket> getTGTTickets(int page, int size) {
        return null;
    }

    public List<Ticket> getTicketsByUsername(String username) {
        return null;
    }

    @Override
    public Ticket getTicketToReUse(Authentication authentication) {
        return null;
    }

    @Override
    public Stream<? extends Ticket> getTicketsStream(Stream<String> keysStream) {
        return null;
    }

    @Override
    public void removeCleanListValue(String value) {

    }

    @Override
    public List<Object> getCleanList(Long i) {
        return null;
    }

    @Override
    public void addToCleanList(String value) {

    }

    public List<Ticket> getChildrenTickets(String ticketId) {
        return null;
    }

    public void deleteIncludeKeyTickets(String ticketId) {
    }

    public static class CachedTicketExpirationPolicy implements Expiry<String, Ticket> {
        private long getExpiration(final Ticket value, final long currentTime) {
            if (value.isExpired()) {
                LOGGER.warn("Ticket [{}] has expired and shall be evicted from the cache", value.getId());
                return 0;
            }
            return currentTime;
        }

        @Override
        public long expireAfterCreate(final String key, final Ticket value, final long currentTime) {
            return getExpiration(value, currentTime);
        }

        @Override
        public long expireAfterUpdate(final String key, final Ticket value, final long currentTime, final long currentDuration) {
            return getExpiration(value, currentDuration);
        }

        @Override
        public long expireAfterRead(final String key, final Ticket value, final long currentTime, final long currentDuration) {
            return getExpiration(value, currentDuration);
        }
    }
}
