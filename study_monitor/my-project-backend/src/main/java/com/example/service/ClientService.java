package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Client;
import com.example.entity.vo.request.CliDetailVO;
import com.example.entity.vo.request.RuntimeDetailVO;

public interface ClientService extends IService<Client> {
   public String getRuntimeInfo(RuntimeDetailVO vo, Client client);
   public boolean registerClient(String token);
   public Client findClientByToken(String token);
   public Client findClientById(int id);
   public String getClientDetail(CliDetailVO vo, Client client);
}
