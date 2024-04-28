package com.example.monitor_clinet.config;

import com.alibaba.fastjson2.JSONObject;
import com.example.monitor_clinet.entity.ConnectionConfig;
import com.example.monitor_clinet.utils.MonitorInfoUtils;
import com.example.monitor_clinet.utils.NetUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
@Configuration
public class ClientServerConfig implements ApplicationRunner {
    @Resource
    NetUtils netUtils;
    @Resource
    MonitorInfoUtils monitorInfoUtils;
    @Bean
    ConnectionConfig connectionConfig() {
        log.info("正在加载连接配置...");
        ConnectionConfig config = readConfigFromFile();
        System.out.println(monitorInfoUtils.monitorBaseDetail());
        if(config==null){
            return registerFromServer();
        }
        return config;
    }
    private ConnectionConfig registerFromServer() {
        Scanner scanner=new Scanner(System.in);
       String ip,token;
       do{
           log.info("请输入服务器IP地址：");
           ip=scanner.nextLine();
           log.info("请输入服务器令牌：");
           token=scanner.nextLine();
       }while (!netUtils.registerToServer(ip,token));
        ConnectionConfig connectionConfig=new ConnectionConfig(ip,token);
        writeConfigToFile(connectionConfig);
        return connectionConfig;
    }
    private void writeConfigToFile(ConnectionConfig config) {
        File dir=new File("config");
        if(!dir.exists()&&dir.mkdir()) log.info("创建配置文件夹...");
        File file=new File("config/server.json");
        try (FileWriter writer = new FileWriter("config/server.json")){
            writer.write(JSONObject.toJSONString(config));
            log.info("配置文件已写入");
        }catch (IOException e){
            log.error("写入配置文件时发生错误："+e.getMessage(),e);
        }
    }
 private ConnectionConfig readConfigFromFile() {
     File configFile = new File("config/server.json");
     if(configFile.exists()){
         try(FileInputStream reader = new FileInputStream(configFile)){
              String raw=new String(reader.readAllBytes(), StandardCharsets.UTF_8) ;
              return JSONObject.parseObject(raw, ConnectionConfig.class);
         }  catch (IOException e) {
             log.error("读取配置文件时发生错误：" + e.getMessage(), e);
             throw new RuntimeException(e);
         }
     }
     return null;
 }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("客户端已启动");
        netUtils.updateBaseDetails(monitorInfoUtils.monitorBaseDetail());
    }
}
