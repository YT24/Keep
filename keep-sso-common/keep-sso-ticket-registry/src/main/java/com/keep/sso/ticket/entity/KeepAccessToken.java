package com.keep.sso.ticket.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class KeepAccessToken implements Ticket,Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 用户id
     */
    private String username;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 上次使用时间
     */
    private LocalDateTime lastTimeUsed;

    /**
     * 存活时间（有效期），单位s：
     * create_time + time_to_live  <  当前时间  有效
     */
    private Long timeToLive;

    /**
     * 死亡时间，单位s：
     * last_time_used + time_to_live  <  当前时间
     */
    private Long timeToDie;

    /**
     * tgt_id
     */
    private String tgtId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 服务ID
     */
    private String serviceId;


    @Override
    public boolean expired() {
        final LocalDateTime currentSystemTime = LocalDateTime.now();
        final LocalDateTime creationTime = this.createTime;
        final LocalDateTime lastTimeUsed = this.lastTimeUsed;

        // Ticket has been used, check maxTimeToLive (hard window)
        LocalDateTime expirationTime;
        if (timeToLive > 0) {
            expirationTime = creationTime.plusSeconds(timeToLive);
            if (currentSystemTime.isAfter(expirationTime)) {
                log.debug("Ticket is expired because the time since creation is greater than maxTimeToLiveInSeconds");
                return true;
            }
        }
        expirationTime = lastTimeUsed.plusSeconds(this.timeToDie);
        if (currentSystemTime.isAfter(expirationTime)) {
            log.debug("Ticket is expired because the time since last use is greater than timeToKillInSeconds");
            return true;
        }
        return false;
    }

    @Override
    public String getDescendantTickets() {
        return null;
    }

    @Override
    public void setDescendantTickets(String descendantTickets) {

    }
}
