package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 实践评估博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PracticeEvaluateBo implements Serializable {

    /**
     * 是否解决
     */
    private Boolean solved;

    /**
     * 用户得分
     */
    private Double score;

    /**
     * 总分
     */
    private Double totalScore;

}
