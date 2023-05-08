package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 停止任务请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class StopTaskReq implements Serializable {

    /**
     * 流程图id
     */
    private Long chartId;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * ip
     */
    private String ip;

    /**
     * 是否使用ip识别设备
     */
    private Boolean isUsingIp;
}
