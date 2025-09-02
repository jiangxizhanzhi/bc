package com.cyberpeace.space.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SystemTreeRes implements Serializable {

    @ApiModelProperty("技战法id")
    private String systemId;

    @ApiModelProperty("技战法名称")
    private String name;

    @ApiModelProperty("防御战法")
    private List<TacticDefenseRes> tacticDefenseList;
}
