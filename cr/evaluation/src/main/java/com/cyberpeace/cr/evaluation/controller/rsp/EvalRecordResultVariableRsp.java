package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordResultVariableRsp {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("变量项名称")
    private String name;

    @ApiModelProperty("格式化算法信息")
    private EvalRecordResultAlgorithmRsp formatAlgorithm;

    @ApiModelProperty("匹配算法信息")
    private EvalRecordResultAlgorithmRsp matchAlgorithm;

    @ApiModelProperty("评分算法信息")
    private EvalRecordResultAlgorithmRsp markAlgorithm;
}
