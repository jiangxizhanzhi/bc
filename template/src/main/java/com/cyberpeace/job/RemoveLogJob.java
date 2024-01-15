package com.cyberpeace.job;

import com.cyberpeace.common.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Slf4j
@Component
public class RemoveLogJob {

    private boolean removeLogStatus;

    @PostConstruct
    public void init() {
        removeLogStatus = Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.removeLogStatus"));
    }


    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void removeLogFiles() {
        if (!Boolean.TRUE.equals(removeLogStatus)) {
            log.info("dev环境下, 不检测日志文件时间超长");
            return;
        }
        String logRootPath = "/var/log/bc/";
        String cmd = String.format("find %s -type 'f' -mtime +15 | xargs rm -f", logRootPath);
        DataOutputStream dos = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            log.info(cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
        }catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Exec shell error! command=[{}], catch error:", cmd, e);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("Successfully remove overdue logs.");
    }
}
