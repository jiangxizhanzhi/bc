package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordResultAlgorithmRsp {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("算法名称")
    private String name;

    @ApiModelProperty("算法描述")
    private String description;

    @ApiModelProperty("算法类型")
    private Integer type;

    @ApiModelProperty("算法使用语言类型")
    private Integer algorithmLanguage;

    @ApiModelProperty("算法代码")
    private String code;

    @ApiModelProperty("配置参数")
    private List<AlgorithmParamContext> params;
}
