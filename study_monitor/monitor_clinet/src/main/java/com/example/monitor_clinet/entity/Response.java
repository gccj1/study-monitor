package com.example.monitor_clinet.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public  class Response {
    private  int id;
    private  int code;
    private  Object data;
    private  String message;

    public Response(int id, int code, Object data, String message) {
        this.id = id;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public boolean success() {
        return code == 200;
    }

    public JSONObject getData() {
        return JSONObject.from(data);
    }

    public String getDataString() {
        return data.toString();
    }

    public static Response errorInfo(Exception e) {
        return new Response(0, 500, null, e.getMessage());
    }


}
