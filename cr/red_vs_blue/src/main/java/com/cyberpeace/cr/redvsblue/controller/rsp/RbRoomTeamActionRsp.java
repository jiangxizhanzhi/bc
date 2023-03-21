package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RbRoomTeamActionRsp implements Serializable {
    @ApiModelProperty("动态事件时间")
    private Date eventTime;

    @ApiModelProperty("事件内容")
    private String content;
}
