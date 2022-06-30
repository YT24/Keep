package para.sso.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "pi_oauth_tokens", indexes = {@Index(name = "idx_create_time", columnList = "create_time")})
@DiscriminatorColumn(name = "type")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public abstract class SimpleOAuthToken implements Ticket {
    private static final long serialVersionUID = -608429899098767392L;

    @Id
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private String id;

    @Column(name = "user", nullable = false)
    private String user;

    @Column(name = "time_to_live")
    protected long expMax;

    @Column(name = "time_to_idle")
    protected long expIdle;

    @Column(name = "last_time_used")
    @Generated(GenerationTime.INSERT)
    private long lTU = 0;

    @Column(name = "create_time")
    private long ct;

    @Column(name = "number_of_times_used")
    private int cou;

    @Column(name = "service_id")
    private String sId;

    @Column(name = "service_original_url")
    private String sOUrl;

    @Column(name = "service_artifact_id")
    private String sAId;

    @Column(name = "tgt_id", nullable = false, length = Integer.MAX_VALUE)
    private String tgtId;

    @Column(name = "expired")
    @JsonIgnore
    private Boolean expired = Boolean.FALSE;

    @Lob
    @Column(name = "extends", length = Integer.MAX_VALUE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String extendFields;

    public SimpleOAuthToken(String id, String user, long expMax,
            long expIdle, long lTU,
            long ct, int cou, Boolean expired,
            String ticketGrantingTicket_id, String sId, String sOUrl, String sAId) {
        this.id = id;
        this.user = user;
        this.expMax = expMax;
        this.expIdle = expIdle;
        this.lTU = lTU;
        this.ct = ct;
        this.cou = cou;
        this.expired = expired;
        this.tgtId = ticketGrantingTicket_id;
        this.sId = sId;
        this.sOUrl = sOUrl;
        this.sAId = sAId;
    }

    public SimpleOAuthToken() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public TicketGrantingTicket getGrantingTicket() {
        return null;
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

    public long getCt() {
        return ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

    public int getCou() {
        return cou;
    }

    public void setCou(int cou) {
        this.cou = cou;
    }

    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    @Override
    public int compareTo(Ticket o) {
        return getId().compareTo(o.getId());
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsOUrl() {
        return sOUrl;
    }

    public void setsOUrl(String sOUrl) {
        this.sOUrl = sOUrl;
    }

    public String getsAId() {
        return sAId;
    }

    public void setsAId(String sAId) {
        this.sAId = sAId;
    }

    public String getTgtId() {
        return tgtId;
    }

    public void setTgtId(String tgtId) {
        this.tgtId = tgtId;
    }

    @Override
    public boolean isExpired() {
        return expired;
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
}
