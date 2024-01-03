package com.cyberpeace.scene.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/25 10:27
 * @desc :
 **/
@Data
@TableName("cr_network")
public class NetworkEntity extends BaseTable {

    @TableField("resource_id")
    private String uuid;

    /**
     * 用户名
     */
    private String user;

    /**
     * 项目名
     */
    private String project;

    /**
     * 网络名
     */
    private String name;

    /**
     * 网络类型
     */
    private String type;

    /**
     * 网络地址
     */
    private String cidr;

    /**
     * 网关地址
     */
    private String gateway;

    /**
     * 物理MTU
     */
    private Integer mtu;

    /**
     * vlan
     */
    private Integer vlan;

    /**
     * 场景id
     */
    private String sid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
