package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.practice.dao.constants.Difficulty;
import com.cyberpeace.oj.practice.dao.constants.PracticeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("理论基础练习")
@TableName("practice_theory_practicetheory")
public class PracticeTheory extends PracticeBase {

    /**
     * 题型
     */
    private Integer questionType;

    /**
     * 选项 - (判断、单选、多选题型使用)
     */
    @TableField("`option`")
    private String option;

    /**
     * 空 - (填空题型使用)
     */
    private String blank;

    /**
     * 关键字 - (简答题题型使用)
     */
    private String keyword;

    // TODO 以下字段均为规避 db exception
    @JsonIgnore
    private Integer practiceType = PracticeType.THEORY.ordinal();

    @JsonIgnore
    private Integer difficulty = Difficulty.PRIMARY.ordinal();

    @JsonIgnore
    private String file = "";

    @JsonIgnore
    private boolean publicOfficialWriteup = false;

    /**
     * 理论基础中并未实际使用此字段, 忽略
     */
    @JsonIgnore
    private String title = "";
}
