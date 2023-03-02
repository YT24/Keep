package com.keep.sso.ticket;

import java.io.Serializable;

public abstract class OAuthToken implements Ticket , Serializable {

    private static final long serialVersionUID = -608429899098767392L;

    private String id;


    private String user;

    /**
     * time_to_live
     */
    protected long expMax;

    protected long expIdle;


    private long ct;


    private String serviceId;

    private String sOUrl;


    private String tgtId;


    private Boolean expired = Boolean.FALSE;


    private String extendFields;


}
