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

        Integer logFileKeepDays = logConfigMessageListener.getLogFileKeepDays();

        // 默认最少保留一天
        return logFileKeepDays < 1 ? 1 : logFileKeepDays;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void removeLogFiles() {
        String logRootPath = System.getProperty("catalina.base") + "/logs/";
        String cmd = String.format("find %s -type 'f' -mtime +%d | xargs rm -f", logRootPath, (getLogFileKeepDays() - 1));
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
            log.info("Remove overdue logs successfully!");
        } catch (Exception e) {
            log.info("Remove overdue logs error! command=[{}], catch error:", cmd, e);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
