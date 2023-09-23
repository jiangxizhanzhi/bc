package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("评估列表数据")
public class EvalRecordListRsp implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("评估状态")
    private Integer status;

    @ApiModelProperty("评分结果")
    private Integer score;

    @ApiModelProperty("关联指标体系ID")
    private String systemId;

    @ApiModelProperty("指标体系名称")
    private String systemName;

    @ApiModelProperty("关联测评任务ID")
    private String taskId;

    @ApiModelProperty("错误信息")
    private String message;

    @ApiModelProperty("评估检索探针开始时间")
    private Date startTime;

    @ApiModelProperty("评估检索探针结束时间")
    private Date endTime;

    @ApiModelProperty("评估开始时间")
    private Date evalTime;
}
