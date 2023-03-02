package com.keep.sso.ticket;


import java.io.Serializable;

public class RefreshToken extends OAuthToken implements Serializable {
    private static final long serialVersionUID = 2171951964928645996L;


    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean expired() {
        return false;
    }
}
