package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

@Data
public class LcActualDevice implements Serializable {

    private String id;

    private Integer deviceType;

    private String name;
}
