package org.example.dto;

public class ApiHeaderDTO {

    /**
     * 请求头 key
     */
    private String name = "";

    /**
     * 注释
     */
    private String desc = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
