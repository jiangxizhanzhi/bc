package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("评估结果列表数据")
public class EvalRoleScoreListRsp implements Serializable {

    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    private Integer role;

    private Integer score;
}
