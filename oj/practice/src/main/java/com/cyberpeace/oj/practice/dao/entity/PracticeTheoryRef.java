package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 派生于PracticeOperation, 在试卷中被引用
 */
@Data
@TableName("practice_theory_ref")
public class PracticeTheoryRef extends PracticeTheory {
    /**
     * 引用原数据resourceID
     */
    private String originId;

    /**
     * 是否选项乱序(选择题使用)
     */
    private Boolean isOptionRandom = Boolean.TRUE;

    /**
     * 所属试卷
     */
    private String testPaper;

}
