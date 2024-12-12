package com.cyberpeace.oj.practice.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 实践评估博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PracticeEvaluateBo implements Serializable {

    /**
     * 是否解决
     *
     * @see Boolean
     */
    private Boolean solved;

    /**
     * 用户得分
     *
     * @see Double
     */
    private Double score;

    /**
     * 总分
     *
     * @see Double
     */
    private Double totalScore;

}
