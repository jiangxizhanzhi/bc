package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("理论基础练习")
public class PracticeTheoryReq{

    @ApiModelProperty(value = "ID", required = true)
    private String resourceId;

    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @ApiModelProperty(value = "习题集", required = true)
    private String practiceSet;

    @ApiModelProperty(value = "分类", required = true)
    private String category;

    @ApiModelProperty(value = "答案", required = true)
    private String answer;

    @ApiModelProperty(value = "题型", required = true)
    private Integer questionType;

    @ApiModelProperty("知识点")
    private String knowledge;

    @ApiModelProperty("选项 - (判断、单选、多选题型使用)")
    private String option;

    @ApiModelProperty("空 - (填空题型使用)")
    private String blank;

    @ApiModelProperty("关键字 - (简答题题型使用)")
    private String keyword;

}
