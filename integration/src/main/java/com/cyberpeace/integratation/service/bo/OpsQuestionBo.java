package com.cyberpeace.integratation.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpsQuestionBo implements Serializable {
    private String resourceId;
    private boolean isPass;
    private String raceId;
    private String name;
    private String desc;
    private String direction;
}
