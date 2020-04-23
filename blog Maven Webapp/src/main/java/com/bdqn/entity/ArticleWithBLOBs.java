package com.bdqn.entity;

import java.io.Serializable;

public class ArticleWithBLOBs extends Article implements Serializable {
    private String content;

    private String cover;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }
}