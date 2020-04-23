package com.bdqn.entity;

import java.io.Serializable;
import java.util.Date;

public class Banner implements Serializable {
    private Integer id;

    private String maintitle;

    private String smalltitle;

    private Date createtime;

    private String userUuid;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle == null ? null : maintitle.trim();
    }

    public String getSmalltitle() {
        return smalltitle;
    }

    public void setSmalltitle(String smalltitle) {
        this.smalltitle = smalltitle == null ? null : smalltitle.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid == null ? null : userUuid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}