package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("db_client")
public class Client implements BaseData {
    @TableId(type = IdType.AUTO)
    int id;
    String name;
    String token;
    String node;
    String location;
    Date createAt;
}
