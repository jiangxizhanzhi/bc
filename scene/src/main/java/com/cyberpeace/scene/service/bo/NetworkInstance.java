package com.cyberpeace.scene.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :   SN
 * @date :   2024/1/4 10:02
 * @desc :  网络对象实例
 **/
@Data
public class NetworkInstance implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 创建人
     */
    private String user;

    /**
     * 项目
     */
    private String project;

    /**
     * 名称
     */
    private String name;

    /**
     * vlan
     */
    private Integer vlan;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 状态
     */
    private String status;

    /**
     * 网段
     */
    private String cidr;

    /**
     * mtu
     */
    private Integer mtu;

    /**
     * 类型
     */
    private String type;
}
