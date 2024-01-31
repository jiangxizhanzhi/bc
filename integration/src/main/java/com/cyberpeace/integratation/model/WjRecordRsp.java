package com.cyberpeace.integratation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WjRecordRsp implements Serializable {

    @ApiModelProperty("任务ID")
    private String id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("报告下载链接")
    private String url;

    @ApiModelProperty("更新时间")
    private Date createTime;

}
