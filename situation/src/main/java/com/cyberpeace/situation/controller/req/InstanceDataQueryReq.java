package com.cyberpeace.situation.controller.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InstanceDataQueryReq {

    @ApiModelProperty(value = "视角类型", required = false)
    private String type;

    @ApiModelProperty(value = "大屏ID", required = true)
    private String dashboardId;

    @ApiModelProperty(value = "特征ID", required = true)
    private String featureId;

    @ApiModelProperty(value = "大屏角色", required = true)
    private String role;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value = "大屏当前天文时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currentTime;

}
