package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.req.CreateNetworkReq;
import com.cyberpeace.scene.service.bo.res.CreateNetworkRes;

/**
 * @author :   SN
 * @date :   2023/12/21 9:16
 * @desc :
 **/
public interface CpCloudService {

    /**
     * 创建网络
     * @param req   创建网络实体
     * @return  响应对象
     */
    CreateNetworkRes createNetwork(CreateNetworkReq req);
}
