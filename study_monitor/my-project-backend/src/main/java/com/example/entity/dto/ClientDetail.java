package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("db_client_detail")
@NoArgsConstructor
public class ClientDetail {
    @TableId
    int id;
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
