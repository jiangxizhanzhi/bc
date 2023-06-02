package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRecordResultIndexRsp implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("指标/变量项名称")
    private String name;

    @ApiModelProperty("评估分值")
    private Integer score;

    @ApiModelProperty("算法ID")
    private String algorithmId;

    @ApiModelProperty("算法名称")
    private String algorithmName;

    @ApiModelProperty("下一级指标/变量项信息")
    private List<EvalRecordResultIndexRsp> children;

    @JsonIgnore
    private Integer level;

    @JsonIgnore
    private String nextIds;

    @JsonIgnore
    private String parentId;

    @ApiModelProperty("算法是否为层次分析法")
    private Integer mode;
}
