package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordStartReq implements Serializable {
    @ApiModelProperty("评估id")
    private String resourceId;
}
