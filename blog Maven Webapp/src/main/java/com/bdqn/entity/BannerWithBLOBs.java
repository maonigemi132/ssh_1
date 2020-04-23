package com.bdqn.entity;

import java.io.Serializable;

public class BannerWithBLOBs extends Banner implements Serializable {
    private String url;

    private String tourl;

    private static final long serialVersionUID = 1L;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTourl() {
        return tourl;
    }

    public void setTourl(String tourl) {
        this.tourl = tourl == null ? null : tourl.trim();
    }
}