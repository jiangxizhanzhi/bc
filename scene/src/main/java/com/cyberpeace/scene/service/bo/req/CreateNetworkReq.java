package com.cyberpeace.scene.service.bo.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/21 9:18
 * @desc :  创建网络请求对象
 **/
@Data
@NoArgsConstructor
public class CreateNetworkReq implements Serializable {

    /**
     * 网络名
     */
    private String name;

    /**
     * 网络地址
     */
    private String cidr;

    /**
     * 网关地址
     */
    private String gateway;

    public CreateNetworkReq(String name, String cidr, String gateway) {
        this.name = name;
        this.cidr = cidr;
        this.gateway = gateway;
    }
}
