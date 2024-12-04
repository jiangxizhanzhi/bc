package com.cyberpeace.oj.practice.controller.req;

import lombok.Data;

/**
 * TestPaperReq使用, 提交试卷中的练习信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Data
public class SinglePracticeReq {

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 执业类型
     *
     * @see Integer
     */
    private Integer practiceType;

    /**
     * 得分
     *
     * @see Float
     */
    private Float score;

    /**
     * 是随机选项
     *
     * @see Boolean
     */
    private Boolean isOptionRandom = Boolean.TRUE;

}
