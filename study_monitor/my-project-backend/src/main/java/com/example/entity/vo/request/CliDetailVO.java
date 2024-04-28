package com.example.entity.vo.request;

import lombok.Data;

@Data
public class CliDetailVO {
    String osArch;
    String osName;
    String osVersion;
    String cpuName;
    int cpuCore;
    int osBit;
    double memory;
    double disk;
    String ip;
}
