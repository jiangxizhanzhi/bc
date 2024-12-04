package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

/**
 * 实践理论转 储加载博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeDumpLoadBo
 * @since 1.0.0
 */
@Data
public class PracticeTheoryDumpLoadBo extends PracticeDumpLoadBo {

    // 理论题独有字段
    /**
     * 题型 单选 多选 判断 填空 简答
     *
     * @see Integer
     */
    private Integer theoryType;

    /**
     * 填空题答案
     *
     * @see String
     */
    private String blank;

    /**
     * 选项
     *
     * @see String
     */
    private String option;

    /**
     * 关键字 简答题答案
     *
     * @see String
     */
    private String keyword;

}
