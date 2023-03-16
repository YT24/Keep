package com.keep.sso.login.filter;

import com.keep.sso.ticket.entity.param.LoginParam;

public interface ILoginFilter {

    boolean doFilter(LoginParam loginParam);
}
