package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRoomIntPairRsp {
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String label;
}
