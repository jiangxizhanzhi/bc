package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RbSituationAdEventRsp implements Serializable {
    @ApiModelProperty("攻击与防护类型")
    private String eventName;

    @ApiModelProperty("目标资产名称, 多个以`, `分隔")
    private String deviceName;

    @ApiModelProperty("事件类型")
    private Date eventTime;
}
