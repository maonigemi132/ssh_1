package com.bdqn.entity;

import java.io.Serializable;

public class SettingWithBLOBs extends Setting implements Serializable {
    private String logourl;

    private String keywords;

    private String descption;

    private static final long serialVersionUID = 1L;

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl == null ? null : logourl.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getDescption() {
        return descption;
    }

    public void setDescption(String descption) {
        this.descption = descption == null ? null : descption.trim();
    }
}