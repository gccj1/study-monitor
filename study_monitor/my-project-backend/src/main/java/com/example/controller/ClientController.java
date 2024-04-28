package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Client;
import com.example.entity.vo.request.CliDetailVO;
import com.example.service.ClientService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor")
public class ClientController {
    @Resource
    ClientService clientService;
    @GetMapping("/register")
    public RestBean<String> clientRegister(@RequestHeader("Authorization") String token) {
        boolean b = clientService.registerClient(token);
        return b? RestBean.success(): RestBean.failure(401,"客户端注册失败，请检查token");
    }

    @PostMapping("/detail")
    public RestBean<String> clientDetail(@RequestBody CliDetailVO vo, @RequestAttribute(Const.ATTR_CLIENT)Client client) {
        String clientDetail = clientService.getClientDetail(vo, client);
        return clientDetail==null ?RestBean.success():RestBean.failure(401,clientDetail);
    }
}
