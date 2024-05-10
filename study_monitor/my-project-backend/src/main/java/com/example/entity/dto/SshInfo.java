package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("db_ssh")
public class SshInfo {
    Integer id;
    Integer port=22;
    String password;
    String username;
    String ip;
}
