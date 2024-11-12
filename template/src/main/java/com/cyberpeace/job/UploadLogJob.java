package com.cyberpeace.job;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传日志作业
 *
 * @author admin
 * @date 2024/11/12
 */
@Slf4j
@Component
public class UploadLogJob {

    /**
     * mp-env服务
     */
    @Autowired
    private MpEnvService mpEnvService;

    /**
     * 报告日志url
     */
    private String REPORT_LOG_URL;

    /**
     * 正在上传日志
     */
    private boolean isUploadLog;

    /**
     * 下载日志url
     */
    private String DOWNLOAD_LOG_URL = "/cs/admin/log/downloadLog?file_name=";

    /**
     * 日志类型
     */
    private String LOG_type = "BC";

    /**
     * 主机地址
     */
    private String HOST_ADDRESS;

    /**
     * init
     *
     * @throws UnknownHostException 未知主机异常
     */
    @PostConstruct
    public void init() throws UnknownHostException {
        REPORT_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.report.log");
        isUploadLog = Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.upload.log"));
        HOST_ADDRESS = SystemPropUtil.getProp("BC_HOST");
    }

    /**
     * 上传日志
     */
    @Scheduled(fixedDelayString = "${common.upload.log.interval.time}")
    public void uploadLog(){
        if(isUploadLog){
            String logRootPath = System.getProperty("catalina.base") + "/logs/";
            List<String> logPaths = FileUtil.listFileByFile(new File(logRootPath),1,false);
            List<JSONObject> logBos = new ArrayList<>();
            for (String logPath: logPaths) {
                String logName = FileUtil.getFileName(logPath,true);
                if(logName.startsWith("bc")){
                    File logFile = new File(logPath);
                    JSONObject object = new JSONObject();
                    object.put("download_url",DOWNLOAD_LOG_URL+logName);
                    object.put("file_name",logName);
                    object.put("file_size",(int)logFile.length());
                    long lastModified = logFile.lastModified();
                    Date date = new Date(lastModified);
                    object.put("update_time",TimeUtil.format1(date));
                    object.put("host_ip",HOST_ADDRESS);
                    logBos.add(object);
                }
            }
            JSONObject object = new JSONObject();
            object.put("list",logBos);
            object.put("log_report_type_enum",LOG_type);
            int uploadLogCount = HttpClientUtil.post(REPORT_LOG_URL,object, Integer.class);
            log.info(String.format("Successfully reported %d logs.", uploadLogCount));
        }
    }
}
