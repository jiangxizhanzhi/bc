package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalIndicatorListReq extends EvalNameListReq {
    @ApiModelProperty("类型查询")
    private Integer attribute;
}
