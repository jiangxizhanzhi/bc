package com.cyberpeace.situation.model.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsAttackSchedule implements Serializable {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private Integer status;

    public WsAttackSchedule(String name, Integer status) {
        this.name = name;
        this.status = status;
    }

    public WsAttackSchedule() {
    }
}
