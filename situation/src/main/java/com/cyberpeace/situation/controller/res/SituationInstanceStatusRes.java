package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SituationInstanceStatusRes implements Serializable {

    private String code;

    @ApiModelProperty("状态值")
    private String desc;

}
