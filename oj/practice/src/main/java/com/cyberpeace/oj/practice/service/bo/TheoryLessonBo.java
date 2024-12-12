package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课后练习使用
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class TheoryLessonBo implements Serializable {

    /**
     * 裁判id
     *
     * @see String
     */
    @ApiModelProperty("快照编号")
    private String refId;

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty("答案")
    private String answer;

    /**
     * 空白计数
     *
     * @see Integer
     */
    @ApiModelProperty("填空题空格数量")
    private Integer blankCount;

    /**
     * 标题
     *
     * @see String
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 选择
     *
     * @see String
     */
    @ApiModelProperty("选项")
    private String option;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("方向名称")
    private String directionName;

    /**
     * 执业类型
     *
     * @see Integer
     */
    @ApiModelProperty("习题类型")
    private Integer practiceType;

    /**
     * 问题类型
     *
     * @see Integer
     */
    @ApiModelProperty("题型")
    private Integer questionType;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
    private Integer difficulty;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
    private String resourceId;

    /**
     * 得分
     *
     * @see Double
     */
    @ApiModelProperty("分值")
    private Double score;

    /**
     * 内置
     *
     * @see Boolean
     */
    @ApiModelProperty("是否内置")
    private Boolean builtin;
}
