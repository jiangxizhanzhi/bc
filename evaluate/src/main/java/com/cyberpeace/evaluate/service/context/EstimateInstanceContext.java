package com.cyberpeace.evaluate.service.context;

import java.util.Date;

import lombok.Data;

@Data
public class EstimateInstanceContext {
    /**评估实例id */
    private String estimateInstanceId;

    /**评估id */
    private String estimateId;

    /**场景id */
    private String sceneId;

    /**评估开始时间 */
    private Date startTime;

    /**评估结束时间 */
    private Date endTime;

    /**评估结果（json） */
    private String result;

    /**状态（评估中、评估停止） */
    private Integer status;

    /**保存状态（1=手动保存，只有手动保存的可被查询） */
    private Integer save;

    private String instanceName;

    private String instanceDesc;
}
