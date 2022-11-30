package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

@Data
public class StopTaskReq implements Serializable {
    private Long chartId;

    private String deviceId;

    private String deviceIp;

    private Boolean isUsingIp;
}
