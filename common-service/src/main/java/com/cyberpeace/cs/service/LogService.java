package com.cyberpeace.cs.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cs.controller.req.LogReq;
import com.cyberpeace.cs.dao.entity.LogEntity;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/14 16:54
 * @desc :
 **/
public interface LogService {

    ListRsp<LogEntity> getRunLogs(LogReq req);

    List<JSONObject> getModules();
}
