package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalRecordRsp implements Serializable {
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

    @ApiModelProperty("指标分数")
    private Integer indexScore;

    @ApiModelProperty("开始时间（评估采集数据时间范围）")
    private Date startTime;

    @ApiModelProperty("结束时间（评估采集数据时间范围）")
    private Date endTime;

    @ApiModelProperty("评估开始时间")
    private Date evalTime;

    @ApiModelProperty("指标体系结构")
    private List<EvalSystemIndexesContext> indexes;
}
