package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.SshInfo;
import com.example.entity.vo.response.SshSettingVO;
import com.example.mapper.ClientMapper;
import com.example.mapper.SshMapper;
import com.example.service.SshService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class SshImp extends ServiceImpl<SshMapper, SshInfo> implements SshService {
    @Resource
    ClientMapper clientMapper;
    @Override
    public void updateSshInfo(SshInfo info) {
        SshInfo sshInfo = new SshInfo();
        BeanUtils.copyProperties(info, sshInfo);
       if(baseMapper.selectById(info.getId())==null) baseMapper.insert(sshInfo);
       else baseMapper.updateById(sshInfo);
    }

    @Override
    public SshSettingVO getSshSetting(int id) {
        SshInfo sshInfo = baseMapper.selectById(id);
        if(sshInfo == null) return new SshSettingVO();
        SshSettingVO settingVO = new SshSettingVO();
        BeanUtils.copyProperties(sshInfo, settingVO);
        return settingVO;
    }
}
