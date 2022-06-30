package para.sso.ticket.enums;

import para.sso.ticket.SimpleAccessTokenImpl;
import para.sso.ticket.SimpleOAuthToken;
import para.sso.ticket.SimpleTicketGrantingTicket;

/**
 * @description:
 * @author: caohh
 * @date: 2021-01-04 15:28
 **/
public enum EntityTypeEnum {

    TGT(SimpleTicketGrantingTicket.class, "SimpleTicketGrantingTicket", "TGT"),
    AT(SimpleAccessTokenImpl.class, "SimpleAccessTokenImpl", "AT"),
    RT(SimpleOAuthToken.class, "SimpleOAuthToken", "RT");

    private Class entityType;

    private String entityName;

    private String prefix;

    public Class getEntityType() {
        return entityType;
    }

    public String getPrefix() {
        return prefix;
    }

    EntityTypeEnum(Class entityType, String entityName, String prefix) {
        this.entityType = entityType;
        this.entityName = entityName;
        this.prefix = prefix;
    }

}
