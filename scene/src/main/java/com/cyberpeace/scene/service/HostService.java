package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.ConsoleUrlRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/27 10:43
 * @desc :
 **/
public interface HostService {

    CreateInstanceRes create(CreateInstanceReq req);

    void delete(String instanceId);

    ConsoleUrlRes console(String instanceId);

    CreateInstanceRes getInstance(String instanceId);

    String getStatus(String instanceId);

    /**
     * 创建快照
     * @param instanceNames instance名称
     */
    void createSnapshot(List<String> instanceNames);

    /**
     * 恢复快照
     * @param instanceNames instance名称
     */
    void revertSnapshot(List<String> instanceNames);
}
