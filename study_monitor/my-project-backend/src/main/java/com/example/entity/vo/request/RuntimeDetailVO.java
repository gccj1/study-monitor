package com.example.entity.vo.request;

import lombok.Data;

@Data
public class RuntimeDetailVO {
    long timestamp;
    double cpuUsage;
    double memoryUsage;
    double cpuTemperature;
    double diskUsage;
    long networkUpload;
    long networkDownload;
    double diskRead;
    double diskWrite;
}
