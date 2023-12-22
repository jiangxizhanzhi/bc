package com.cyberpeace.scene.service.bo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/21 9:18
 * @desc :  创建网络请求对象
 **/
@Data
public class CreateNetworkReq implements Serializable {

    private String user;

    private String project;

    private String name;

    private String type;

    private String cidr;

    private String gateway;

    private Integer mtu;

    private Integer vlan;
}
