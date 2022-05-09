package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class PracticeSubmitReq implements Serializable {

    @ApiModelProperty("习题类型 0=理论基础 2=实操练习")
    @NotNull
    private Integer practiceType;

    @ApiModelProperty("提交的答案")
    @NotBlank
    private String submitAnswer;

    @ApiModelProperty("习题编号")
    @NotBlank
    private String practiceId;

    @ApiModelProperty("是否需要判题")
    private Boolean needEvaluate = false;

    @ApiModelProperty("题型")
    private Integer questionType;

}
