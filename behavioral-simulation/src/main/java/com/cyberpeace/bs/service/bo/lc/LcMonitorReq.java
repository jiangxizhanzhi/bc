package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

@Data
public class LcMonitorReq implements Serializable {

    private Integer deviceId;

    private String deviceIp;

    private Boolean isUsingIp;

    private Long startTime;

    private Integer interval = 1;
}
