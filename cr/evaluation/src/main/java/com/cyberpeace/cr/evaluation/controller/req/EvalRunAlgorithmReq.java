package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRunAlgorithmReq implements Serializable {

    @ApiModelProperty("算法id")
    private String algorithmId;

    @ApiModelProperty("数据")
    private Object data;

    @ApiModelProperty("列表数据")
    private Object listData;

    @ApiModelProperty("其他参数")
    private Object otherArgs;
}
