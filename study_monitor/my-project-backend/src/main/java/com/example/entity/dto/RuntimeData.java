package com.example.entity.dto;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;

@Data
@Measurement(name = "runtime")
public class RuntimeData {
    @Column(tag = true)
    int clientId;
    @Column(timestamp = true)
    Instant timestamp;
    @Column
    double cpuUsage;
    @Column
    double memoryUsage;
    @Column
    double diskUsage;
    @Column
    long networkUpload;
    @Column
    long networkDownload;
    @Column
    double diskRead;
    @Column
    double diskWrite;
}