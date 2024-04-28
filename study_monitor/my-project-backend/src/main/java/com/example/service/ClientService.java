package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Client;
import com.example.entity.vo.request.CliDetailVO;

public interface ClientService extends IService<Client> {
   public boolean registerClient(String token);
   public Client findClientByToken(String token);
   public Client findClientById(int id);
   public String getClientDetail(CliDetailVO vo, Client client);
}
