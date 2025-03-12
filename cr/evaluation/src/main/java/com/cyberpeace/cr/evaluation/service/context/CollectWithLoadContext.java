package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CollectWithLoadContext extends CollectWithStrContext {

    @ApiModelProperty(value = "研判结果(0:未研判 1:成功 2:失败)", required = false)
    private Integer judgmentStatus;

    @ApiModelProperty(value = "值", required = false)
    private Integer value;

}