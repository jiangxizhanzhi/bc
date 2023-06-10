package com.cyberpeace.job;

import com.cyberpeace.service.impl.LogConfigMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class RemoveLogJob {

    @Autowired
    private LogConfigMessageListener logConfigMessageListener;

    /**
     * 获取日志文件保持天数
     *
     * @return {@code Integer}
     */
    public Integer getLogFileKeepDays() {
        return logConfigMessageListener.getLogFileKeepDays();
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void removeLogFiles() {
        String logRootPath = System.getProperty("catalina.base") + "/logs/";
        String cmd = String.format("find %s -type 'f' -mtime +%d | xargs rm -f", logRootPath, getLogFileKeepDays());
        log.info("Remove overdue logs, command=[{}]", cmd);
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
