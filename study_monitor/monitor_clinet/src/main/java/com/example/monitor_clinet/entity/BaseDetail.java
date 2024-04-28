package com.example.monitor_clinet.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseDetail {
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
