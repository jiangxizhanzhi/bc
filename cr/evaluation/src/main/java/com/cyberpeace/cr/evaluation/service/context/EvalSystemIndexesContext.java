package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalSystemIndexesContext implements Serializable {
    @ApiModelProperty("资源id")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("下一级")
    private List<EvalSystemIndexesContext> children;

    @ApiModelProperty("评分方式")
    private Integer type;

    @ApiModelProperty("主机节点ID")
    private String deviceId;

    @ApiModelProperty("探针ID")
    private String probeId;

    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;

    @ApiModelProperty("指标项载荷记录")
    private EvalRecordLoadListContext recordLoad;
}
