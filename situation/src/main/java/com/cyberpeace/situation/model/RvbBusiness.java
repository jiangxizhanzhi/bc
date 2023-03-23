package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RvbBusiness implements Serializable {
    private String resourceId;

    private String mpSceneId;

    private Integer businessCode;

    private String businessInstanceId;
}
