package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课后练习使用
 */
@Data
@ApiModel
public class TheoryLessonBo implements Serializable {

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("填空题空格数量")
    private Integer blankCount;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("选项")
    private String option;

    @ApiModelProperty("方向名称")
    private String directionName;

    @ApiModelProperty("习题类型")
    private Integer practiceType;

    @ApiModelProperty("题型")
    private Integer questionType;

    @ApiModelProperty("难度")
    private Integer difficulty;

    @ApiModelProperty("资源编号")
    private String resourceId;

    @ApiModelProperty("分值")
    private Double score;

    @ApiModelProperty("是否内置")
    private Boolean builtin;
}
