package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习评估请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeEvaluateReq {

    /**
     * 练习类型
     */
    @ApiModelProperty("练习类型")
    private Integer practiceType;

    /**
     * 资源id
     */
    @ApiModelProperty("练习ID")
    private String resourceId;

    /**
     * 答案
     */
    @ApiModelProperty("提交答案")
    private String answer;
}
