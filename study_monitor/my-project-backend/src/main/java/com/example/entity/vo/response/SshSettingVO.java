package com.example.entity.vo.response;

import lombok.Data;

@Data
public class SshSettingVO {
    String ip;
    Integer port=22;
    String username;
    String password;
}
