package com.bdqn.entity;

import java.io.Serializable;

public class UserWithBLOBs extends User implements Serializable {
    private String photourl;

    private String qrcodeurl;

    private static final long serialVersionUID = 1L;

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl == null ? null : photourl.trim();
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl == null ? null : qrcodeurl.trim();
    }
}