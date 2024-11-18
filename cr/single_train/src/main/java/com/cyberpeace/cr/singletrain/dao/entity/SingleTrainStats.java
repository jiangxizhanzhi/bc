package com.cyberpeace.cr.singletrain.dao.entity;

import lombok.Data;

/**
 * 单训练统计
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class SingleTrainStats {
    /**
     * 业务模板id
     *
     * @see String
     */
    private String businessTemplateId;

    /**
     * 日期
     *
     * @see String
     */
    private String date;

    /**
     * 计数
     *
     * @see Integer
     */
    private Integer count;
}
