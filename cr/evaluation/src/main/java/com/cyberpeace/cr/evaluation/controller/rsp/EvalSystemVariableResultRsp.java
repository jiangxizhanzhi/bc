package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 评估系统变量结果响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalSystemVariableResultRsp {

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty(value = "名称")
	private String name;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty(value = "ID")
	private String resourceId;

    /**
     * exe 循环
     *
     * @see Integer
     */
    @ApiModelProperty(value = "执行轮次")
	private Integer exeCycle;

    /**
     * 第一分
     *
     * @see Integer
     */
    @ApiModelProperty(value = "一级得分")
	private Integer firstScore;

    /**
     * Second Score 第二分
     *
     * @see Integer
     */
    @ApiModelProperty(value = "二级得分")
	private Integer secondScore;

    /**
     * 第三分
     *
     * @see Integer
     */
    @ApiModelProperty(value = "三级得分")
	private Integer thirdScore;

    /**
     * 第四分
     *
     * @see Integer
     */
    @ApiModelProperty(value = "四级得分")
	private Integer fourthScore;

    /**
     * 第五分
     *
     * @see Integer
     */
    @ApiModelProperty(value = "五级得分")
	private Integer fifthScore;

}