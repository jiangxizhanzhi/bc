package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

/**
 * 练习理论导出加载Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PracticeTheoryDumpLoadBo extends PracticeDumpLoadBo {

    // 理论题独有字段
    /**
     * 题型 单选 多选 判断 填空 简答
     */
    private Integer theoryType;

    /**
     * 填空题答案
     */
    private String blank;

    /**
     * 选项
     */
    private String option;

    /**
     * 关键字 简答题答案
     */
    private String keyword;

}
