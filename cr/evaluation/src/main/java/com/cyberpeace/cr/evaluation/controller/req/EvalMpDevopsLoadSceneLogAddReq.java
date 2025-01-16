package com.cyberpeace.cr.evaluation.controller.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalMpDevopsLoadSceneLogAddReq implements Serializable {
    /**
     * 主键
     */
    private Integer pk;
    /**
     * ID
     */
    private String id;
    /**
     * 结果日志
     */
    private String resultLog;
    /**
     * 工具名称
     */
    private String toolName;
    /**
     * 结果状态
     */
    private String resultStatus;

    /**
     * 研判结果(0:未研判 1:成功 2:失败)
     */
    private Integer judgmentStatus;

    /**
     * 备注 研判结果原因
     */
    private String remark;

    /**
     * 载荷执行记录id
     */
    private String executionRecordId;
    /**
     * 阶段id
     */
    private String stageId;

}
