package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CrLabelGroup implements Serializable {
    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("名称")
    private String label;
}
