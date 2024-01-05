package com.cyberpeace.auth.job;

import java.util.*;


import com.cyberpeace.auth.constant.OnlineTimeCache;
import com.cyberpeace.auth.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserOnlineTimeJob {

    @Autowired
    private SysUserService sysUserService;
    @Value("${redis.maxInactiveIntervalInSeconds}")
    int maxInactiveIntervalInSeconds;

    private static long lastTime = 0;

    @Scheduled(fixedRate = 1000*60)
    public void checkAlive() {
        if (System.currentTimeMillis()-lastTime<maxInactiveIntervalInSeconds*500){
            return;
        }
        lastTime = System.currentTimeMillis();
        for (Map.Entry<String, Date> entry : OnlineTimeCache.loginTimeMap.entrySet()) {
            Date date = OnlineTimeCache.remove(entry.getKey());
            sysUserService.updateOnlineTime(date,entry.getKey());
        }
    }


}
