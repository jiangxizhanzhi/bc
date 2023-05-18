package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class EvaluationListReq implements Serializable {

    @NotNull
    @ApiModelProperty(value = "训练ID")
    private String roomId;

    @ApiModelProperty(value = "学员ID")
    private String userId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "评估状态")
    private Integer evaluateStatus;
}
