package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Client;
import com.example.entity.dto.ClientDetail;
import com.example.entity.vo.request.CliDetailVO;
import com.example.entity.vo.request.RenameNodeVO;
import com.example.entity.vo.request.RenameVo;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.response.ClientPreviewVO;
import com.example.entity.vo.response.RuntimeCardVO;
import com.example.mapper.CliDetailMapper;
import com.example.mapper.ClientMapper;
import com.example.service.ClientService;
import com.example.utils.InfluxUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientServiceImp extends ServiceImpl<ClientMapper, Client> implements ClientService{
        private   String registerToken=this.generateToken();
        private final   Map<Integer,Client> clientIdMap=new ConcurrentHashMap<>();
        private final   Map<String,Client> clientTokenMap=new ConcurrentHashMap<>();
        private final   Map<Integer,RuntimeDetailVO> RuntimeMap=new ConcurrentHashMap<>();
        @PostConstruct
        void init(){
            clientTokenMap.clear();
            clientIdMap.clear();
            list().forEach(this::addCache);
        }
    @Resource
    CliDetailMapper detailMapper;
    @Resource
    InfluxUtils influxUtils;

    @Override
    public String getToken() {
        return registerToken;
    }

    @Transactional
    @Override
    public String deleteClient(int id) {
        boolean b = removeById(id);
        int i = detailMapper.deleteById(id);
        RuntimeMap.remove(id);
        init();
        if(!b || i==0) return "发生一些错误,删除失败";
        return null;
    }

    @Override
    public RuntimeDetailVO getRuntimeNow(int id) {
        return RuntimeMap.get(id);
    }

    @Override
    public RuntimeCardVO getRuntimeCard(int id) {
        RuntimeCardVO runtimeCardVO = influxUtils.readRuntimeData(id);
        ClientDetail clientDetail = detailMapper.selectById(id);
        BeanUtils.copyProperties(clientDetail,runtimeCardVO);
        return runtimeCardVO;
    }

    @Override
    public void renameNode(RenameNodeVO vo) {
        update().eq("id", vo.getId())
                .set("node", vo.getNode())
                .set("location", vo.getLocation())
                .update();
        init();
    }

    @Override
    public boolean renameClient(RenameVo vo) {
        boolean update = update().eq("id", vo.getId()).set("name", vo.getName()).update();
        init();
        return update;
    }

    @Override
    public String getRuntimeInfo(RuntimeDetailVO vo, Client client) {
        RuntimeMap.put(client.getId(),vo);
        influxUtils.writeData(client,vo);
        return null;
    }

    @Override
    public boolean registerClient(String token) {
        if(registerToken.equals(token)){
            Client client = new Client();
            client.setName("未命名主机");client.setToken(token);client.setCreateAt(new Date());
            client.setNode("未命名节点");client.setLocation("cn");
            if(save(client)){
                addCache(client);
                registerToken=generateToken();
                return true;
            }
        }
        return false;
    }

    @Override
    public String getClientDetail(CliDetailVO vo, Client client) {
        ClientDetail clientDetail = new ClientDetail();
        BeanUtils.copyProperties(vo,clientDetail);
        clientDetail.setMemory(Math.round(vo.getMemory()*10)/10.0);
        clientDetail.setDisk(Math.round(vo.getDisk()*10)/10.0);
        clientDetail.setCpuName((vo.getCpuName().trim()));
        clientDetail.setId(client.getId());
        int count=0;
        if(detailMapper.selectById(client.getId())==null){
           count= detailMapper.insert(clientDetail);
        }
        else{
          count= detailMapper.updateById(clientDetail);
        }
        return count>0?  null:"信息更新失败";
    }

    @Override
    public List<ClientPreviewVO>getClientPreviewList() {
       return clientTokenMap.values().stream().map(client -> {
            ClientPreviewVO vo=client.asViewObject(ClientPreviewVO.class);
            BeanUtils.copyProperties(detailMapper.selectById(client.getId()),vo);
            RuntimeDetailVO runtimeDetailVO = RuntimeMap.get(client.getId());
            if(runtimeDetailVO!=null && System.currentTimeMillis()-runtimeDetailVO.getTimestamp()< 30*1000){
                BeanUtils.copyProperties(runtimeDetailVO,vo);
                vo.setOnline(true);
            }
            return vo;
        }).toList();
    }

    @Override
    public ClientPreviewVO getCardDetails(int id) {
        ClientPreviewVO clientPreviewVO = new ClientPreviewVO();
        RuntimeDetailVO runtimeDetailVO = Optional.ofNullable(RuntimeMap.get(id)).orElse(new RuntimeDetailVO());
        BeanUtils.copyProperties(detailMapper.selectById(id),clientPreviewVO);
        BeanUtils.copyProperties(clientIdMap.get(id),clientPreviewVO);
        if(System.currentTimeMillis()-runtimeDetailVO.getTimestamp()< 30*1000){
          clientPreviewVO.setOnline(true);
        }
        return clientPreviewVO;
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
