package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("oj_practice_theory")
public class PracticeTheory extends PracticeBase {

    /** 题型 */
    private Integer theoryType;

    @TableField("`option`")
    private String option;

    private String keyword;

}
