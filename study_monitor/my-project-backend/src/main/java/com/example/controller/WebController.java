package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.SshInfo;
import com.example.entity.vo.request.RenameNodeVO;
import com.example.entity.vo.request.RenameVo;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.response.ClientPreviewVO;
import com.example.entity.vo.response.RuntimeCardVO;
import com.example.entity.vo.response.SshSettingVO;
import com.example.service.ClientService;
import com.example.service.SshService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/monitor")
public class WebController {
    @Resource
    ClientService clientService;
    @Resource
    SshService sshService;

    private <T> RestBean<T> handleMessage(Supplier<T> supplier) {
        T t = supplier.get();
        return t==null ? RestBean.success():RestBean.failure(401,t.toString());
    }
    @GetMapping("/list")
    public RestBean<List<ClientPreviewVO>> getClientList() {
        return RestBean.success(clientService.getClientPreviewList()) ;
    }
    @PostMapping("/rename")
    public RestBean<Void> renameClient(@RequestBody RenameVo vo) {
        boolean update = clientService.renameClient(vo);
        return update? RestBean.success():RestBean.failure(401,"更新主机名失败");
    }
    @GetMapping("/details")
    public RestBean<ClientPreviewVO> getClientDetails(@RequestParam("id") int id) {
        return RestBean.success(clientService.getCardDetails(id));
    }
    @PostMapping("/node")
    public RestBean<Void> renameNode(@RequestBody @Valid RenameNodeVO vo) {
        clientService.renameNode(vo);
        return RestBean.success();
    }
    @GetMapping("/runtime-history")
    public RestBean<RuntimeCardVO> getRuntimeHistory(@RequestParam("id") int id) {
        return RestBean.success(clientService.getRuntimeCard(id));
    }
    @GetMapping("runtime-now")
    public RestBean<RuntimeDetailVO> getRuntimeNow(@RequestParam("id") int id) {
        return RestBean.success(clientService.getRuntimeNow(id));
    }
    @GetMapping("register")
    public RestBean<String> register() {
        return RestBean.success(clientService.getToken());
    }
    @GetMapping("/delete")
    public RestBean<String> delete(@RequestParam("id") int id) {
        return handleMessage(()-> clientService.deleteClient(id));
    }
    @GetMapping("/ssh")
    public RestBean<SshSettingVO> getSshSetting(@RequestParam("id") int id) {
        return RestBean.success(sshService.getSshSetting(id));
    }
    @PostMapping("/ssh-save")
    public RestBean<Void> saveSSHSetting(@RequestBody SshInfo vo) {
        sshService.updateSshInfo(vo);
        return RestBean.success();
    }
}
