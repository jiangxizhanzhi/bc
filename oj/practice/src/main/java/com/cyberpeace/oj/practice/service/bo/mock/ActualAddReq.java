package com.cyberpeace.oj.practice.service.bo.mock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActualAddReq {
    @ApiModelProperty(value = "真题试卷模板id")
    private String resourceId;
}
