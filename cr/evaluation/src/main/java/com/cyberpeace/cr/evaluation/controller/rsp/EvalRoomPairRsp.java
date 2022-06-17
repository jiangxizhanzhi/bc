package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRoomPairRsp implements Serializable {
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("名称")
    private String label;
}
