package com.keep.sso.ticket;


public class AccessToken extends OAuthToken {
    private static final long serialVersionUID = -60175457238560722L;


    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean expired() {
        return false;
    }
}
