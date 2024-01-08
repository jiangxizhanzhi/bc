package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.ContainerItem;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.res.ConsoleUrlRes;
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/21 19:55
 * @desc :
 **/
public interface ContainerService {

    CreateContainersRes create(CreateContainerReq req);

    void delete(String containerId);

    String console(String containerId);

    ContainerItem getInstance(String containerId);

    String getStatus(String containerId);
}
