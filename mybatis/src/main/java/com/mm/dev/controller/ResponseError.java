package com.mm.dev.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 通用错误响应信息
 * <p>
 * Created by GaryGuo on 2015/8/13.
 */
public class ResponseError {

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJSONString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String errorJSON = objectMapper.writer().writeValueAsString(this);
        return errorJSON;
    }
}
