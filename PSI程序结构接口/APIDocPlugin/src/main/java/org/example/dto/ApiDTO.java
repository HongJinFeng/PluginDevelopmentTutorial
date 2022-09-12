package org.example.dto;

import java.io.Serializable;
import java.util.List;

public class ApiDTO {
    /**
     * 路径
     */
    private String path;
    /**
     * 请求参数
     */
    private ApiBodyDTO requestBody;
    /**
     * 头信息
     */
    private List<ApiHeaderDTO> header;

    private ApiBodyDTO responseBody;

    private List<ApiRequestParamDTO> params;

    private String method;

    /**
     * 描述
     */
    private String desc;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ApiHeaderDTO> getHeader() {
        return header;
    }

    public void setHeader(List<ApiHeaderDTO> header) {
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ApiBodyDTO getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(ApiBodyDTO requestBody) {
        this.requestBody = requestBody;
    }

    public ApiBodyDTO getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ApiBodyDTO responseBody) {
        this.responseBody = responseBody;
    }

    public List<ApiRequestParamDTO> getParams() {
        return params;
    }

    public void setParams(List<ApiRequestParamDTO> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.method).append("        ").append(this.path).append("<br>").append("------------------------------------------").append("<br>");
        result.append("请求参数").append("<br>").append("参数类型        ").append("参数名称        ").append("注释").append("<br>");
        if (this.getParams() != null) {
            for (ApiRequestParamDTO param : this.getParams()) {
                result.append(param.getType()).append(" ".repeat(Math.max(0, 18 - param.getType().length())));
                result.append(param.getName()).append(" ".repeat(Math.max(0, 18 - param.getName().length())));
                result.append(param.getDesc()).append(" ".repeat(Math.max(0, 18 - param.getDesc().length()))).append("<br>");
            }
            result.append("------------------------------------------").append("<br>");
        }
        if (this.getRequestBody() != null) {
            if (this.getRequestBody().getBodyDto() == null) {
                result.append(this.getRequestBody().getType()).append(" ".repeat(Math.max(0, 18 - this.getRequestBody().getType().length())));
                result.append(this.getRequestBody().getName()).append(" ".repeat(Math.max(0, 18 - this.getRequestBody().getName().length())));
                result.append(this.getRequestBody().getDesc()).append(" ".repeat(Math.max(0, 18 - this.getRequestBody().getDesc().length()))).append("<br>");
            } else {
                for (ApiBodyDTO param : this.getRequestBody().getBodyDto()) {
                    result.append(param.getType()).append(" ".repeat(Math.max(0, 18 - param.getType().length())));
                    result.append(param.getName()).append(" ".repeat(Math.max(0, 18 - param.getName().length())));
                    result.append(param.getDesc()).append(" ".repeat(Math.max(0, 18 - param.getDesc().length()))).append("<br>");
                }
            }
            result.append("------------------------------------------").append("<br>");
        }
        result.append("<br><br>").append("响应参数").append("<br>").append("响应类型        ").append("参数名称        ").append("注释").append("<br>");
        if (this.getResponseBody().getBodyDto() == null) {
            result.append(this.getResponseBody().getType()).append(" ".repeat(Math.max(0, 18 - this.getResponseBody().getType().length())));
            result.append(this.getResponseBody().getName()).append(" ".repeat(Math.max(0, 18 - this.getResponseBody().getName().length())));
            result.append(this.getResponseBody().getDesc()).append(" ".repeat(Math.max(0, 18 - this.getResponseBody().getType().length()))).append("<br>");
        } else {
            for (ApiBodyDTO param : this.getResponseBody().getBodyDto()) {
                result.append(param.getType()).append(" ".repeat(Math.max(0, 18 - param.getType().length())));
                result.append(param.getName()).append(" ".repeat(Math.max(0, 18 - param.getName().length())));
                result.append(param.getDesc()).append(" ".repeat(Math.max(0, 18 - param.getDesc().length()))).append("<br>");
            }
        }
        return result.toString();
    }

}
