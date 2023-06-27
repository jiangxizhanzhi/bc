package com.cyberpeace.job;

import com.cyberpeace.service.impl.LogConfigMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class RemoveLogJob {

    @Autowired
    private LogConfigMessageListener logConfigMessageListener;

    // 上一次执行日志清理的日期
    private String lastClearPoint = "";

    /**
     * 检查是否为清理日志时间段
     */
    @Scheduled(fixedRate = 30 * 1000)
    public void checkTime() {

        // 获取当前时间
        LocalTime now = LocalTime.now();

        // 获取开始时间：0点
        LocalTime startTime = LocalTime.MIDNIGHT;

        // 获取结束时间：0点30分
        LocalTime endTime = LocalTime.of(0, 30);

        // 获取当前时间年月日
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // 判断当天日志是否已清理
        Boolean isClear = Objects.equals(lastClearPoint, date);

        // 判断当前时间是否在0点到0点30分之间
        if (now.isAfter(startTime) && now.isBefore(endTime) && Boolean.TRUE.equals(!isClear)) {
            removeLogFiles(date);
        }
    }

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

    public void removeLogFiles(String date) {
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
            lastClearPoint = date;
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
