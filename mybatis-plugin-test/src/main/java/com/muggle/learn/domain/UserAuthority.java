package com.muggle.learn.domain;

import java.util.Date;

public class UserAuthority {
    /**
    * 主键
    */
    private Long id;

    /**
    * url
    */
    private String url;

    /**
    * 描述
    */
    private String description;

    /**
    * 创建时间
    */
    private Date gmtCreate;

    /**
    * 是否可用
    */
    private Boolean enable;

    /**
    * 请求类型
    */
    private String requestType;

    /**
    * 应用名
    */
    private String application;

    /**
    * 类名
    */
    private String className;

    /**
    * 方法名
    */
    private String methodName;

    /**
    * 父id
    */
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}