package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordResultRsp extends EvalRecordRsp {
    @ApiModelProperty("评估得分")
    private Integer score;

    @ApiModelProperty("运行错误信息")
    private String message;

    @ApiModelProperty("评价等级")
    private String gradeStr;

}
