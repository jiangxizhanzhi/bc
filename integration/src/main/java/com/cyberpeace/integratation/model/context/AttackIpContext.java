package com.cyberpeace.integratation.model.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttackIpContext implements Serializable {

    private String sceneId;

    private String sourceIps;

    private String ips;

    private Integer ipType;
}
