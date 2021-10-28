package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PracticeRandomChoiceReq {
    @ApiModelProperty("习题集查询")
    private String practiceSet;

    @ApiModelProperty("分类查询")
    private String category;

    @ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
    private Integer questionType;

    @ApiModelProperty("内容查询")
    private String pattern;

    @NotBlank
    @ApiModelProperty("获取数量")
    private Integer count;

    @NotBlank
    @ApiModelProperty("设置默认分值")
    private Double score;
}
