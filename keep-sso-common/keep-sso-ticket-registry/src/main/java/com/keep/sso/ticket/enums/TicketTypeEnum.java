package com.keep.sso.ticket.enums;

import com.keep.sso.ticket.entity.KeepAccessToken;
import com.keep.sso.ticket.entity.KeepRefreshToken;
import com.keep.sso.ticket.entity.KeepTgtToken;


public enum TicketTypeEnum {

    TGT(KeepTgtToken.class, "KeepTgtToken", "TGT"),
    AT(KeepAccessToken.class, "KeepAccessToken", "AT"),
    RT(KeepRefreshToken.class, "KeepRefreshToken", "RT");

    private Class ticketType;

    private String entityName;

    private String prefix;

    public Class getEntityType() {
        return ticketType;
    }

    public String getPrefix() {
        return prefix;
    }

    TicketTypeEnum(Class entityType, String entityName, String prefix) {
        this.ticketType = entityType;
        this.entityName = entityName;
        this.prefix = prefix;
    }

}
