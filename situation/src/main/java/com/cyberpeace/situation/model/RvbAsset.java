package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RvbAsset implements Serializable {
    private String deviceId;

    private String roleType;
}
