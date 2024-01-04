package com.cyberpeace.scene.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :   辛发圣
 * @date :   2023/12/28 9:33
 * @desc :
 **/
@Data
public class ImageItem implements Serializable {

    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 用户
     */
    private String user;

    /**
     * 项目
     */
    private String project;

    /**
     * 镜像名称
     */
    private String name;

    /**
     * 镜像状态
     */
    private String status;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 镜像格式
     */
    private String diskFormat;

    /**
     * 镜像校验值 md5
     */
    private String checksum;

    /**
     * 最小磁盘
     */
    private Integer minDisk;

    /**
     * 最小内存
     */
    private Integer minMemory;

    /**
     * 最小CPU
     */
    private Integer minCpu;

    /**
     * 镜像描述
     */
    private String description;

    /**
     * 镜像类型
     */
    private String imageType;

    /**
     * 镜像位置
     */
    private String location;

    /**
     * 是否支持hw_qemu_guest_agent
     */
    private boolean hwQemuGuestAgent;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;
}
