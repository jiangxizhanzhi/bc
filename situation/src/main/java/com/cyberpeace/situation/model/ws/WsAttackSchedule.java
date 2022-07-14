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
    private String runAttackMessageName;

    public WsAttackSchedule(String stageName, Boolean run, String runAttackMessageName) {
        this.stageName = stageName;
        this.run = run;
        this.runAttackMessageName = runAttackMessageName;
    }

    public WsAttackSchedule() {
    }
}
