package com.cyberpeace.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RemoveLogJob {


    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void removeLogFiles() {
        String logRootPath = System.getProperty("catalina.base") + "/logs/";
        String cmd = String.format("find %s  -type 'f' -mtime +15 |xargs rm -f", logRootPath);
        try {
            Runtime.getRuntime().exec(cmd);
            log.info(cmd);
        } catch (IOException e) {
            log.error("Exec shell error! command=[{}], catch error:", cmd, e);
        }
        log.info("Successfully remove overdue logs.");
    }
}
