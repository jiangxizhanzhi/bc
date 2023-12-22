package com.cyberpeace.scene.service.bo.res;

import com.cyberpeace.scene.service.bo.req.CreateNetworkReq;
import lombok.Data;


/**
 * @author :   SN
 * @date :   2023/12/21 9:20
 * @desc :  创建网络响应对象
 **/
@Data
public class CreateNetworkRes extends CreateNetworkReq {

    private String uuid;
}
