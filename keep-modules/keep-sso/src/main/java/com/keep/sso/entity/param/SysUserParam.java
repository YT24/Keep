package com.keep.sso.entity.param;

import lombok.Data;

@Data
public class SysUserParam {

    private String username;

    private String password;

    private String realName;

    private String mobile;

    private String email;

    private Integer deptId;
}
