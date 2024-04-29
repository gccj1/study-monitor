package com.example.entity.vo.request;

import lombok.Data;

@Data
public class RuntimeDetailVO {
    long timestamp;
    double cpuUsage;
    double memoryUsage;
    double diskUsage;
    double networkUpload;
    double networkDownload;
    double diskRead;
    double diskWrite;
}
