package com.example.utils;

import com.example.entity.dto.Client;
import com.example.entity.dto.RuntimeData;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InfluxUtils {
    @Value("${spring.influx.url}")
    String url;
    @Value("${spring.influx.user}")
    String user;
    @Value("${spring.influx.password}")
    String password;
    private InfluxDBClient dbClient;
    String bucket = "data";
    String org = "gccj";
    @PostConstruct
    public void init() {
        dbClient = InfluxDBClientFactory.create(url, user, password.toCharArray());
    }
    public void writeData(Client client, RuntimeDetailVO vo){
        RuntimeData runtimeData = new RuntimeData();
        BeanUtils.copyProperties(vo, runtimeData);
        runtimeData.setTimestamp(new Date().toInstant());
        runtimeData.setClientId(client.getId());
        WriteApiBlocking writeApi = dbClient.getWriteApiBlocking();
        writeApi.writeMeasurement(bucket, org, WritePrecision.NS, runtimeData);
    }
}
