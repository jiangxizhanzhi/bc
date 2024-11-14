package com.cyberpeace.oj.practice.controller.req;

import lombok.Data;

/**
 * TestPaperReq使用, 提交试卷中的练习信息
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SinglePracticeReq {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 练习类型
     */
    private Integer practiceType;

    /**
     * 得分
     */
    private Float score;

    /**
     * 是否选项随机
     */
    private Boolean isOptionRandom = Boolean.TRUE;

}
