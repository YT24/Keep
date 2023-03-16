package com.keep.sso.login.filter;

import com.keep.sso.ticket.entity.param.LoginParam;

public class UsernamePwdFilter extends BaseLoginFilter{
    @Override
    public boolean doFilter(LoginParam loginParam) {

        if(true){
            sendMsg();
        }

        return false;
    }
}
