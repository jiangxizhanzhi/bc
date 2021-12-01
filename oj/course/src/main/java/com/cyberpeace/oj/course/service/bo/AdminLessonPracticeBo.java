package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLessonPracticeBo implements Serializable {

    @ApiModelProperty("答案")
    private String answer;

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

}
