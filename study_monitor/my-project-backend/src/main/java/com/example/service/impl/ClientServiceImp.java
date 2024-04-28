package com.example.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Client;
import com.example.mapper.ClientMapper;
import com.example.service.ClientService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientServiceImp extends ServiceImpl<ClientMapper, Client> implements ClientService{
    @Resource
    DefaultIdentifierGenerator identifierGenerator;
        private final String registerToken=generateToken();
        private final Map<Integer,Client> clientIdMap=new ConcurrentHashMap<>();
        private final Map<String,Client> clientTokenMap=new ConcurrentHashMap<>();

        @PostConstruct
        void init(){
            list().forEach(this::addCache);
        }
    @Override
    public boolean registerClient(String token) {
        if(registerToken.equals(token)){
            Client client = new Client();
            client.setName("未命名主机");client.setToken(token);client.setCreateAt(new Date());
            if(save(client)){
                addCache(client);
                return true;
            }
        }
        return false;
    }

    @Override
    public Client findClientById(int id) {
        return clientIdMap.get(id);
    }

    @Override
    public Client findClientByToken(String token) {
        return clientTokenMap.get(token);
    }

    private void addCache(Client client){
        clientIdMap.put(client.getId(),client);
        clientTokenMap.put(client.getToken(),client);
    }
   private String generateToken() {
        String template = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder();
       SecureRandom secureRandom=new SecureRandom();
        for (int i = 0; i<32; i++) {
            int index = secureRandom.nextInt(template.length());
            token.append(template.charAt(index));
        }
       System.out.println("token: " + token);
       return token.toString();
   }
}
