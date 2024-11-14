package com.cyberpeace.cr.singletrain.dao.entity;

import lombok.Data;

/**
 * 单训练统计
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SingleTrainStats {
    /**
     * 业务模板id
     */
    private String businessTemplateId;

    /**
     * 日期
     */
    private String date;

    /**
     * 计数
     */
    private Integer count;
}
