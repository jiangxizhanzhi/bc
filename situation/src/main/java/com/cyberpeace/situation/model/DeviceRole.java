package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceRole implements Serializable {

    @ApiModelProperty("靶标角色id")
    private Integer id;

    @ApiModelProperty("靶标角色名称")
    private String name;

}
