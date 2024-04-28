package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("db_client")
public class Client {
    @TableId(type = IdType.AUTO)
    int id;
    String name;
    String token;
    Date createAt;
}
