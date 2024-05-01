package com.example.monitor_clinet.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RuntimeDetail {
    long timestamp;
    double cpuUsage;
    double memoryUsage;
    double diskUsage;
    long networkUpload;
    long networkDownload;
    double diskRead;
    double diskWrite;
}
