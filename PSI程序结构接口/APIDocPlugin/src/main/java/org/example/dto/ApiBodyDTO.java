package org.example.dto;

import java.util.List;

public class ApiBodyDTO {

    private List<ApiBodyDTO> bodyDto;

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

    public List<ApiBodyDTO> getBodyDto() {
        return bodyDto;
    }

    public void setBodyDto(List<ApiBodyDTO> bodyDto) {
        this.bodyDto = bodyDto;
    }

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
