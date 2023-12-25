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

    /**
     * 用户名
     */
    private String user;

    /**
     * 项目名
     */
    private String project;

    /**
     * 网络类型
     */
    private String type;

    /**
     * 物理MTU
     */
    private Integer mtu;

    /**
     * vlan
     */
    private Integer vlan;

    /**
     * 网络id
     */
    private String uuid;
}
