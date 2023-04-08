package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RbSituationActionRsp implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("动态时间")
    private Date actionTime;

    @ApiModelProperty("动态类型, 1=普通; 2=完成（绿色字体样式）")
    private Integer type;
}
