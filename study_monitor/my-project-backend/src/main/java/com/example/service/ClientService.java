package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Client;
import com.example.entity.vo.request.CliDetailVO;
import com.example.entity.vo.request.RenameNodeVO;
import com.example.entity.vo.request.RenameVo;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.response.ClientPreviewVO;
import com.example.entity.vo.response.RuntimeCardVO;

import java.util.List;

public interface ClientService extends IService<Client> {
   public boolean renameClient(RenameVo vo);
   public String getRuntimeInfo(RuntimeDetailVO vo, Client client);
   public boolean registerClient(String token);
   public Client findClientByToken(String token);
   public Client findClientById(int id);
   public String getClientDetail(CliDetailVO vo, Client client);
   public List<ClientPreviewVO> getClientPreviewList();
   public ClientPreviewVO getCardDetails(int id);
   public  void renameNode(RenameNodeVO vo);

   public RuntimeCardVO getRuntimeCard(int id);
   public RuntimeDetailVO getRuntimeNow(int id);
   public String deleteClient(int id);
   public String getToken();
}
