package com.cyberpeace.cs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.controller.req.LogReq;
import com.cyberpeace.cs.dao.entity.LogEntity;
import com.cyberpeace.cs.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :   SN
 * @date :   2023/12/14 16:54
 * @desc :
 **/
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Override
    public ListRsp<LogEntity> getRunLogs(LogReq req) {
        //获取日志文件存放路径
        String rootPath = System.getProperty("catalina.base") + "/logs/";
        List<LogEntity> logs = new ArrayList<>();
        logFiles(new File(rootPath), logs);
        if (req.getEnd() != null && req.getStart()!= null){
            logs = logs.stream().filter(a -> a.getUpdateTime().getTime() >= req.getStart().getTime()
                    && a.getUpdateTime().getTime() <= req.getEnd().getTime()).collect(Collectors.toList());
        }
        return PageUtil.pageList(logs, req.getPage(), req.getPerPage());
    }

    @Override
    public List<JSONObject> getModules() {
        List<JSONObject> list = new ArrayList<>();
        for (LogEntity.Module module : LogEntity.Module.values()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", module.getCode());
            jsonObject.put("name", module.getName());
            jsonObject.put("en", module.getEn());
            list.add(jsonObject);
        }
        return list;
    }

    /**
     * 递归遍历所有日志文件
     * @param file 日志存放文件夹
     * @param logs  日志文件
     * @return  日志文件
     */
    private void logFiles(File file, List<LogEntity> logs){
        if (file.isFile() && file.getName().startsWith("bc")) {
            LogEntity log = new LogEntity(file.getName()
                    ,new Date(file.lastModified()), String.valueOf(file.length()));
            logs.add(log);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                logFiles(f, logs);
            }
        }
    }
}
