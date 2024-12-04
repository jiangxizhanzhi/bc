package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实践评估请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Data
public class PracticeEvaluateReq {

    /**
     * 执业类型
     *
     * @see Integer
     */
    @ApiModelProperty("练习类型")
    private Integer practiceType;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("练习ID")
    private String resourceId;

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty("提交答案")
    private String answer;
}
