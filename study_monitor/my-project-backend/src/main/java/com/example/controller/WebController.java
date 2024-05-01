package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.vo.request.RenameVo;
import com.example.entity.vo.response.ClientPreviewVO;
import com.example.service.ClientService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class WebController {
    @Resource
    ClientService clientService;
    @GetMapping("/list")
    public RestBean<List<ClientPreviewVO>> getClientList() {
        return RestBean.success(clientService.getClientPreviewList()) ;
    }
    @PostMapping("/rename")
    public RestBean<Void> renameClient(@RequestBody RenameVo vo) {
        boolean update = clientService.renameClient(vo);
        return update? RestBean.success():RestBean.failure(401,"更新主机名失败");
    }
}
