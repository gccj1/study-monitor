package com.example.monitor_clinet.task;

import com.example.monitor_clinet.entity.RuntimeDetail;
import com.example.monitor_clinet.utils.MonitorInfoUtils;
import com.example.monitor_clinet.utils.NetUtils;
import jakarta.annotation.Resource;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class MonitorRuntimeTask extends QuartzJobBean {
    @Resource
    NetUtils netUtils;
    @Resource
    MonitorInfoUtils infoUtils;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        RuntimeDetail runtimeDetail = infoUtils.monitorRuntimeDetail();
        netUtils.updateRuntimeDetails(runtimeDetail);
        System.out.println(runtimeDetail);
    }
}
