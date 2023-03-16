package com.keep.sso.login.filter;

import com.keep.sso.ticket.entity.param.LoginParam;
import lombok.Data;

@Data
public class LoginContext {

    private LoginParam loginParam;
}
