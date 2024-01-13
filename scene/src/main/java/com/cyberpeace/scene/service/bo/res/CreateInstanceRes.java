package com.cyberpeace.scene.service.bo.res;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.scene.service.bo.Net;

import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/29 9:25
 * @desc :
 **/
@Data
public class CreateInstanceRes implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 虚拟机id
     */
    private String uuid;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
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
     * 虚拟机名称
     */
    private String instanceName;

    /**
     * 镜像
     */
    private String image;

    /**
     * 虚拟机状态
     */
    private String powerState;

    /**
     * 主机名
     */
    private String hostname;

    /**
     * 启动时间
     */
    private Date launchAt;

    /**
     * 虚拟机状态
     */
    private String vmState;

    private String taskState;

    /**
     * 虚拟机地址
     */
    private JSONObject addresses;

    /**
     * 虚拟机名称
     */
    private String name;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 系统架构
     */
    private String architecture;

    /**
     * 虚拟机描述
     */
    private String description;

    /**
     * 内存
     */
    private Integer memory;

    /**
     * cpu
     */
    private Integer cpu;

    /**
     * 磁盘
     */
    private Integer disk;

	/**
	 * 端口映射参数
	 */
	private List<Net> nets;
}
