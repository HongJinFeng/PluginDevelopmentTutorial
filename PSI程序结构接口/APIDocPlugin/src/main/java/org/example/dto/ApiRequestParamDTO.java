package org.example.dto;

public class ApiRequestParamDTO {

    /**
     * 参数名字
     */
    private String name = "";

    /**
     * 参数类型
     */
    private String type = "";

    /**
     * 参数注释
     */
    private String desc = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
