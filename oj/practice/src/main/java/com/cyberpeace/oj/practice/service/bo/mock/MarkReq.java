package com.cyberpeace.oj.practice.service.bo.mock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarkReq {
    private String resourceId;

    @ApiModelProperty(value = "true标记，false取消标记")
    private boolean mark;
}
