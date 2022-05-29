package org.example.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslatorUtils {

    private final static String transAPIHost = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    public static String appid;
    public static String securityKey;

    public static String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        String resp = HttpUtils.get(transAPIHost, params);
        TransResp transResp = JSON.parseObject(resp, TransResp.class);
        if (transResp.getTransResult() == null || transResp.getTransResult().size() == 0) {
            return "翻译出错";
        }
        return transResp.getTransResult().get(0).getDst();
    }

    private static Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appid);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5Utils.md5(src));
        return params;
    }

    public static class TransResp {

        private String from;
        private String to;
        @JsonProperty("trans_result")
        private List<TransResult> transResult;

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFrom() {
            return this.from;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getTo() {
            return this.to;
        }

        public void setTransResult(List<TransResult> transResult) {
            this.transResult = transResult;
        }

        public List<TransResult> getTransResult() {
            return this.transResult;
        }
    }

    public static class TransResult {
        private String src;
        private String dst;

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSrc() {
            return this.src;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getDst() {
            return this.dst;
        }
    }
}
