package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.EvalRecordIndexContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRecordAddReq implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty(value = "靶场测评实例ID", required = true)
    private String taskId;

    @ApiModelProperty(value = "使用指标体系ID", required = true)
    private String systemId;

    @ApiModelProperty(value = "开始时间（评估采集数据时间范围）", required = true)
    private String startTime;

    @ApiModelProperty(value = "结束时间（评估采集数据时间范围）", required = true)
    private String endTime;

    @ApiModelProperty(value = "全量三级指标项", required = true)
    private List<EvalRecordIndexContext> indexes;
}
