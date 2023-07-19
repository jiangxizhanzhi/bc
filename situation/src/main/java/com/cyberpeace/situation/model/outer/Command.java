package com.cyberpeace.situation.model.outer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Command implements Serializable {

    @JsonProperty("srcSystemCode")
    @ApiModelProperty("命令源系统code")
    private String srcSystemCode;

    @JsonProperty("dstSystemCode")
    @ApiModelProperty("命令目标系统code")
    private String dstSystemCode;

    @JsonProperty("command")
    @ApiModelProperty("命令")
    private String command;

    @JsonProperty("commandType")
    @ApiModelProperty("命令类型（get)")
    private String commandType;

    @JsonProperty("commandSubtype")
    @ApiModelProperty("命令code")
    private String commandSubtype;

    @JsonProperty("commandResult")
    @ApiModelProperty("命令结果")
    private String commandResult;

    @JsonProperty("resultDesc")
    @ApiModelProperty("结果描述")
    private String resultDesc;

    @JsonProperty("getPlanParams")
    @ApiModelProperty("getPlanParams")
    private Object getPlanParams;

    @JsonProperty("setPlanInfo")
    @ApiModelProperty("setPlanInfo")
    private Object setPlanInfo;

    @JsonProperty("deletePlanInfo")
    @ApiModelProperty("deletePlanInfo")
    private Object deletePlanInfo;

    @JsonProperty("getPlanStatus")
    private Object getPlanStatus;

}
