package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 练习提交请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PracticeSubmitReq implements Serializable {

    /**
     * 练习类型
     */
    @ApiModelProperty("习题类型 0=理论基础 2=实操练习")
    @NotNull
    private Integer practiceType;

    /**
     * 提交答案
     */
    @ApiModelProperty("提交的答案")
    @NotBlank
    private String submitAnswer;

    /**
     * 练习id
     */
    @ApiModelProperty("习题编号")
    @NotBlank
    private String practiceId;

    /**
     * 需要评估
     */
    @ApiModelProperty("是否需要判题")
    private Boolean needEvaluate = false;

    /**
     * 问题类型
     */
    @ApiModelProperty("题型")
    private Integer questionType;

}
