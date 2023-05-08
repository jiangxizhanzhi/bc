package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程流量监控请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcMonitorReq implements Serializable {

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 设备ip
     */
    private String deviceIp;

    /**
     * 是否使用ip识别设备
     */
    private Boolean isUsingIp;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 时间间隔
     */
    private Integer interval = 1;
}
