package com.cyberpeace.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 删除日志工作
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class RemoveLogJob {

    /**
     * 删除日志文件
     *
     * @throws
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void removeLogFiles() {
        String logRootPath = System.getProperty("catalina.base") + "/logs/";
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
