package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.ConsoleUrlRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;

/**
 * @author :   SN
 * @date :   2023/12/27 10:43
 * @desc :
 **/
public interface HostService {

    CreateInstanceRes create(CreateInstanceReq req);

    void delete(String instanceId);

    ConsoleUrlRes console(String instanceId);

     String status(String instanceId);
}
