package com.example.entity.vo.response;

import lombok.Data;

@Data
public class ClientPreviewVO {
    int id;
    boolean online;
    String name;
    String node;
    String location;
    String osName;
    String osVersion;
    String ip;
    String cpuName;
    int cpuCore;
    double memory;
    double cpuUsage;
    double memoryUsage;
    long networkUpload;
    long networkDownload;
}
