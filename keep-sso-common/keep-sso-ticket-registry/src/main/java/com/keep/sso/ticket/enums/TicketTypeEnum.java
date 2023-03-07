package com.keep.sso.ticket.enums;

import com.keep.common.core.expection.BizExpection;
import com.keep.sso.ticket.entity.KeepAccessToken;
import com.keep.sso.ticket.entity.KeepRefreshToken;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.entity.Ticket;
import lombok.SneakyThrows;


public enum TicketTypeEnum {

    TGT(KeepTgtToken.class, "com.keep.sso.ticket.entity.KeepTgtToken", "TGT"),
    AT(KeepAccessToken.class, "com.keep.sso.ticket.entity.KeepAccessToken", "AT"),
    RT(KeepRefreshToken.class, "com.keep.sso.ticket.entity.KeepRefreshToken", "RT");

    private Class<Ticket> ticketType;

    private String entityName;

    private String prefix;

    public Class getEntityType() {
        return ticketType;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getEntityName() {
        return entityName;
    }

    TicketTypeEnum(Class entityType, String entityName, String prefix) {
        this.ticketType = entityType;
        this.entityName = entityName;
        this.prefix = prefix;
    }

    @SneakyThrows
    public static Class<Ticket> getClassByTicketId(String ticketId){
        for (TicketTypeEnum value : TicketTypeEnum.values()) {
            if(ticketId.startsWith(value.getPrefix())){
                return value.getEntityType();
            }
        }
        throw new BizExpection("ticketId is error,should start with TGT or RT or AT");
    }

}
