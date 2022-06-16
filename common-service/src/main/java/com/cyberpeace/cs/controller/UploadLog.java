package com.cyberpeace.cs.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class UploadLog {

    @Autowired
    private MpEnvService mpEnvService;

    private String REPORT_LOG_URL;

    @PostConstruct
    public void init() {{
        REPORT_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.report.log");
    }}

    @Scheduled(fixedDelayString = "${upload.log.interval.time}")
    public void uploadLog(){
        String logLootPath = System.getProperty("catalina.base") + "/logs/";
        List<String> logPaths = FileUtil.listFileByFile(new File(logLootPath),1,false);
        List<JSONObject> logBos = new ArrayList<>();
        for (String logPath: logPaths) {
            String logName = FileUtil.getFileName(logPath,true);
            if(logName.startsWith("bc")){
                File log = new File(logPath);
                JSONObject object = new JSONObject();
                object.put("download_url","/cs/admin/log/downloadLog?file_name="+logName);
                object.put("file_name",logName);
                object.put("file_size",(int)log.length());
                Long lastModified = log.lastModified();
                Date date = new Date(lastModified);
                object.put("update_time",TimeUtil.format1(date));
                logBos.add(object);
            }
        }
        JSONObject object = new JSONObject();
        object.put("list",logBos);
        object.put("log_report_type_enum","BC");
        HttpClientUtil.post(REPORT_LOG_URL,object, Integer.class);
    }
}
