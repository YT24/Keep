package com.example.shardingjdbc.bean;

import lombok.Setter;

public class Goods {
    @Setter
    private Long gid;

    @Setter
    private String gname;

    @Setter
    private Long userId;

    @Setter
    private String gstatus;

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setGstatus(String gstatus) {
        this.gstatus = gstatus;
    }

    public Long getGid() {
        return gid;
    }

    public String getGname() {
        return gname;
    }

    public Long getUserId() {
        return userId;
    }

    public String getGstatus() {
        return gstatus;
    }
}