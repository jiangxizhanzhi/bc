package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理员课程练习 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class AdminLessonPracticeBo implements Serializable {

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty("答案")
    private String answer;

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
     * 裁判id
     *
     * @see String
     */
    @ApiModelProperty("快照编号")
    private String refId;

    /**
     * 得分
     *
     * @see Double
     */
    @ApiModelProperty("分值")
    private Double score;

}
