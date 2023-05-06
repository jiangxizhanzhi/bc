package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程实体设备信息
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcActualDevice implements Serializable {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 系统类型
     */
    private Integer system;
}
