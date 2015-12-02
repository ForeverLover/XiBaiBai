package com.jph.xibaibai.model.entity;

public class Version {

    private String id;
    private String version;//版本
    private String name;//版本名称
    private String content;//版本更新内容
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Version(String id, String version, String name, String content) {
        super();
        this.id = id;
        this.version = version;
        this.name = name;
        this.content = content;
    }

    public Version() {
        super();
        // TODO Auto-generated constructor stub
    }
}
