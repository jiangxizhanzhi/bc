package com.cyberpeace.space.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TacticDefenseRes implements Serializable {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("防御战法id")
    private String defenseId;
}
