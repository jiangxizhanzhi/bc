package com.cyberpeace.cr.evaluation.controller.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTaskBo implements Serializable {

    private String resourceId;

    private String name;

    private Integer missionScore;

    private Integer roleGroup;

    private Integer score;

    private String roleId;

}
