package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

/**
 * 试卷练习
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Data
public class TestPaperPractice {
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 起源id
     *
     * @see String
     */
    private String originId;

    /**
     * 标题
     *
     * @see String
     */
    private String title;

    /**
     * 内容
     *
     * @see String
     */
    private String content;

    /**
     * 得分
     *
     * @see Float
     */
    private Float score;

    /**
     * 执业类型
     *
     * @see String
     */
    private String practiceType;

    /**
     * 问题类型
     *
     * @see String
     */
    private String questionType;

    /**
     * 选择
     *
     * @see String
     */
    private String option;

    /**
     * 是 random 选项
     *
     * @see Boolean
     */
    private Boolean isRandomOption;

    /**
     * 关键词
     *
     * @see String
     */
    private String keyword;

    /**
     * 文件
     *
     * @see String
     */
    private String file;

    /**
     * 文件名称
     *
     * @see String
     */
    private String fileName;

}
