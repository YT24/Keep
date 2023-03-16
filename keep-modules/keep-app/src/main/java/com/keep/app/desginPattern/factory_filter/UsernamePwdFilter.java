package com.keep.app.desginPattern.factory_filter;

public class UsernamePwdFilter extends BaseLoginFilter{
    @Override
    public boolean doFilter(LoginParam loginParam) {

        if(true){
            sendMsg();
        }

        return false;
    }
}
