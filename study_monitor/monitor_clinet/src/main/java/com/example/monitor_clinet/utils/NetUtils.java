package com.example.monitor_clinet.utils;

import com.alibaba.fastjson2.JSONObject;
import com.example.monitor_clinet.entity.ConnectionConfig;
import com.example.monitor_clinet.entity.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class NetUtils {
    @Lazy
    @Resource
    ConnectionConfig config;
    HttpClient client= HttpClient.newHttpClient();
    public boolean registerToServer(String address,String token){
        log.info("Registering to server...");
        Response response = doGet("/register", address, token);
        if(response.success()){
            log.info("Registration successful");
        }
        else{
            log.info("Registration failed: {}", response.getMessage());
        }
        return response.success();
    }
    private Response doGet(String url){
        return doGet(url, config.getIp(), config.getToken());
    }
    private Response doGet(String url, String ip, String token){
        try {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create(ip+"/monitor"+url))
                    .header("Authorization", token)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return JSONObject.parseObject(response.body()).to(Response.class);
        } catch (Exception e) {
            log.error("Error in doGet: {}", e.getMessage());
            return Response.errorInfo(e);
        }
    }
}
