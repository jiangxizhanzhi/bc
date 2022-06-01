package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordStartReq implements Serializable {
    @ApiModelProperty("评估id")
    private String resourceId;
}
