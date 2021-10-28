package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 派生于PracticeOperation, 在试卷中被引用
 */
@Data
@TableName("oj_practice_theory_ref")
public class PracticeTheoryRef extends PracticeOperationRef {

    /** 选项是否随机*/
    private Boolean optionRandom;

}
