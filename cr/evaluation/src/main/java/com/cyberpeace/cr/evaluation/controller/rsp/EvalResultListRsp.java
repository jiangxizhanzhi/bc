package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("评估结果列表数据")
public class EvalResultListRsp implements Serializable {

    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    private String record;

    private Integer weight;

    private Integer indexScore;

    private Integer finalScore;

    private Integer type;
}
