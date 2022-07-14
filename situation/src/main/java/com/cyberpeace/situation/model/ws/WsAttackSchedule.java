package com.cyberpeace.situation.model.ws;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsAttackSchedule implements Serializable {

    @ApiModelProperty("阶段名称")
    private String stageName;

    @ApiModelProperty("是否正在运行")
    private Boolean run;

    @ApiModelProperty("当前运行动效")
    private String runAttackMessage;

    public WsAttackSchedule(String stageName, Boolean run, String runAttackMessage) {
        this.stageName = stageName;
        this.run = run;
        this.runAttackMessage = runAttackMessage;
    }

    public WsAttackSchedule() {
    }
}
