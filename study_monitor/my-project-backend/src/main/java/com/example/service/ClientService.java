package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Client;
import com.example.entity.vo.request.CliDetailVO;
import com.example.entity.vo.request.RenameVo;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.response.ClientPreviewVO;

import java.util.List;

public interface ClientService extends IService<Client> {
   public boolean renameClient(RenameVo vo);
   public String getRuntimeInfo(RuntimeDetailVO vo, Client client);
   public boolean registerClient(String token);
   public Client findClientByToken(String token);
   public Client findClientById(int id);
   public String getClientDetail(CliDetailVO vo, Client client);
   public List<ClientPreviewVO> getClientPreviewList();
}
