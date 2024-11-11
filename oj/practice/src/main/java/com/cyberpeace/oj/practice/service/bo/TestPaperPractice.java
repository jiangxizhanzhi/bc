package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

/**
 * 试卷练习
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class TestPaperPractice {
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 起源id
     */
    private String originId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 得分
     */
    private Float score;

    /**
     * 练习类型
     */
    private String practiceType;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 选择
     */
    private String option;

    /**
     * 是 random 选项
     */
    private Boolean isRandomOption;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 文件
     */
    private String file;

    /**
     * 文件名称
     */
    private String fileName;

}
