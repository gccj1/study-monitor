package com.example.utils;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.dto.Client;
import com.example.entity.dto.RuntimeData;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.response.RuntimeCardVO;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
    public RuntimeCardVO readRuntimeData(int clientId) {
        RuntimeCardVO vo = new RuntimeCardVO();
        String query = """
                from(bucket: "%s")
                |> range(start: %s)
                |> filter(fn: (r) => r["_measurement"] == "runtime")
                |> filter(fn: (r) => r["clientId"] == "%s")
                """;
        String format = String.format(query, bucket, "-1h", clientId);
        List<FluxTable> tables = dbClient.getQueryApi().query(format, org);
        int size = tables.size();
        if (size == 0) return vo;
        List<FluxRecord> records = tables.get(0).getRecords();
        for (int i = 0; i < records.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("timestamp", records.get(i).getTime());
            for (int j = 0; j < size; j++) {
                FluxRecord record = tables.get(j).getRecords().get(i);
                object.put(record.getField(), record.getValue());
            }
            vo.getHistory().add(object);
        }
        return vo;
    }
}
