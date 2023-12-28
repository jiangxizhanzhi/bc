package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/21 19:55
 * @desc :
 **/
public interface ContainerService {

    void create(CreateContainerReq req);


}
