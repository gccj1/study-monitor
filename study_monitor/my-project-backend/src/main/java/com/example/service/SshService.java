package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.SshInfo;
import com.example.entity.vo.response.SshSettingVO;

public interface SshService extends IService<SshInfo> {
    void updateSshInfo(SshInfo info);
    SshSettingVO getSshSetting(int id);
}
