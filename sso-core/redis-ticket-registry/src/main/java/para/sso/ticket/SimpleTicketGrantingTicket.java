package para.sso.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import para.sso.ticket.support.TicketGrantingTicketExpirationPolicy;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

@Entity
@Table(name = "pi_ticket_granting_ticket", indexes = {@Index(name = "idx_create_time", columnList = "create_time")})
@DiscriminatorColumn(name = "type")
@DiscriminatorValue(TicketGrantingTicket.PREFIX)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class SimpleTicketGrantingTicket implements Ticket {
    private static final long serialVersionUID = -608429899098767392L;

    @Id
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private String id;

    @Column(name = "user", nullable = false)
    private String user;

    @Column(name = "time_to_live")
    private long expMax;

    @Column(name = "time_to_idle")
    private long expIdle;

    @Column(name = "last_time_used", insertable = false)
    @Generated(GenerationTime.INSERT)
    private long lTU = 0;

    @Column(name = "previous_last_time_used")
    private long pLTU = 0;

    @Column(name = "create_time")
    private long ct;

    @Column(name = "number_of_times_used")
    private int cou;

    @Lob
    @Column(name = "descendant_tickets", nullable = false, length = Integer.MAX_VALUE)
    private HashSet<String> dt = new HashSet<>();

    @Column(name = "expired", nullable = false)
    @JsonIgnore
    private Boolean expired = Boolean.FALSE;

    @Lob
    @Column(name = "extends", length = Integer.MAX_VALUE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ef;

    public SimpleTicketGrantingTicket() {
    }

    public SimpleTicketGrantingTicket(String id, String user, long expirationPolicyMaxTimeToLiveInSeconds,
                                      long expIdle, long lTU,
            long pLTU, long ct, int cou, Boolean expired,
                                      HashSet<String> dt) {
        this.id = id;
        this.user = user;
        this.expMax = expirationPolicyMaxTimeToLiveInSeconds;
        this.expIdle = expIdle;
        this.lTU = lTU;
        this.pLTU = pLTU;
        this.ct = ct;
        this.cou = cou;
        this.expired = expired;
        this.dt = dt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public TicketGrantingTicket getGrantingTicket() {
        return null;
    }

    @Override
    public ZonedDateTime getCreationTime() {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(ct);
        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    @Override
    public int getCountOfUses() {
        return cou;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getExpMax() {
        return expMax;
    }

    public void setExpMax(long expMax) {
        this.expMax = expMax;
    }

    public long getExpIdle() {
        return expIdle;
    }

    public void setExpIdle(long expIdle) {
        this.expIdle = expIdle;
    }

    public long getlTU() {
        return lTU;
    }

    public void setlTU(long lTU) {
        this.lTU = lTU;
    }

    public long getpLTU() {
        return pLTU;
    }

    public void setpLTU(long pLTU) {
        this.pLTU = pLTU;
    }

    public long getCt() {
        return ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

    public int getCou() {
        return cou;
    }

    @Override
    public ExpirationPolicy getExpirationPolicy() {
        return new TicketGrantingTicketExpirationPolicy(expMax,
                expIdle);
    }

    @Override
    public String getPrefix() {
        return TicketGrantingTicket.PREFIX;
    }

    public void setCou(int cou) {
        this.cou = cou;
    }

    public HashSet<String> getDt() {
        return dt;
    }

    public String getEf() {
        return ef;
    }

    public void setEf(String ef) {
        this.ef = ef;
    }

    @Override
    public int compareTo(Ticket o) {
        return getId().compareTo(o.getId());
    }


}
