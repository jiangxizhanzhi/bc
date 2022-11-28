package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

@Data
public class LcActualDevice implements Serializable {

    private String deviceId;

    private String deviceName;

    private Integer system;
}
