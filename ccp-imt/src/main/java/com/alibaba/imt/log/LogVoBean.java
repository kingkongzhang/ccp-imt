package com.alibaba.imt.log;

public class LogVoBean{
    /**
     * logger��name
     */
    private String name;
    /**
     * logger��level
     */
    private String level;
    /**
     * �ж���log4j����logback��logger
     */
    private String type;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
