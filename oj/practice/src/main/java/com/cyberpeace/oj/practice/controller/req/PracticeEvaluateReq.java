package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PracticeEvaluateReq {

    @ApiModelProperty("练习类型")
    private Integer practiceType;

    @ApiModelProperty("练习ID")
    private String resourceId;

    @ApiModelProperty("提交答案")
    private String answer;
}
