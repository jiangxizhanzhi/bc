package com.cyberpeace.job;

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
public class UploadLogJob {

    @Autowired
    private MpEnvService mpEnvService;

    private String REPORT_LOG_URL;

    private boolean isUploadLog;

    private String DOWNLOAD_LOG_URL = "/cs/admin/log/downloadLog?file_name=";

    private String LOG_type = "BC";

    @PostConstruct
    public void init() {{
        REPORT_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.report.log");
        isUploadLog = Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.upload.log"));
    }}

    @Scheduled(fixedDelayString = "${common.upload.log.interval.time}")
    public void uploadLog(){
        if(isUploadLog){
            String logRootPath = System.getProperty("catalina.base") + "/logs/";
            List<String> logPaths = FileUtil.listFileByFile(new File(logRootPath),1,false);
            List<JSONObject> logBos = new ArrayList<>();
            for (String logPath: logPaths) {
                String logName = FileUtil.getFileName(logPath,true);
                if(logName.startsWith("bc")){
                    File log = new File(logPath);
                    JSONObject object = new JSONObject();
                    object.put("download_url",DOWNLOAD_LOG_URL+logName);
                    object.put("file_name",logName);
                    object.put("file_size",(int)log.length());
                    long lastModified = log.lastModified();
                    Date date = new Date(lastModified);
                    object.put("update_time",TimeUtil.format1(date));
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
