package com.cyberpeace.cr.evaluation.service.bo;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评估查询体
 */
@Data
public class EvalMissionEvaluationBo extends PageReqBase {

    @ApiModelProperty(value = "测评模版ID")
    private String evalTemplateId;

    @ApiModelProperty(value = "学员ID")
    private String studentHash;

    @ApiModelProperty(value = "评估方式")
    private String evaluateType;

    @ApiModelProperty(value = "评估状态")
    private Integer evaluateStatus;

}
