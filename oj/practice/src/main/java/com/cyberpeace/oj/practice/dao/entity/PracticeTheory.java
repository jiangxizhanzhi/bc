package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.practice.dao.constants.PracticeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("practice_theory")
public class PracticeTheory extends PracticeBase {

    @JsonIgnore
    @TableField(exist = false)
    private Integer practiceType = PracticeType.THEORY.getValue();

    /**
     * 题型
     */
    @ApiModelProperty("理论基础题型")
    private Integer questionType;

    /**
     * 选项 - (单选、多选、判断题型使用)
     */
    @ApiModelProperty("选项. 单选、多选、判断题型使用, json字符串形式.")
    @TableField("`option`")
    private String option;


    /**
     * 关键字 - (简答题题型使用)
     */
    @ApiModelProperty("简答题使用")
    private String keyword;

    /**
     * 理论基础中并未实际使用此字段, 忽略
     */
    @JsonIgnore
    private String title;

}
