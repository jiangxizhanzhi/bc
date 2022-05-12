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

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("靶场测评实例ID")
    private String roomId;

    @ApiModelProperty("使用指标体系ID")
    private String systemId;

    @ApiModelProperty("开始时间（评估采集数据时间范围）")
    private String startTime;

    @ApiModelProperty("结束时间（评估采集数据时间范围）")
    private String endTime;

    @ApiModelProperty("全量三级指标项")
    private List<EvalRecordIndexContext> indexes;
}
