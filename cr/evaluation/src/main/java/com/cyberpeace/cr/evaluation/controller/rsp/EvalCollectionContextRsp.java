package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;

/**
 * 评估收集上下文响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalCollectionContextRsp implements Serializable {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 格式算法id
     */
    private String formatAlgorithmId;

    /**
     * 格式算法名称
     */
    private String formatAlgorithmName;

    /**
     * 检波算法id
     */
    private String detectionAlgorithmId;

    /**
     * 检波算法名称
     */
    private String detectionAlgorithmName;

    /**
     * 得分算法id
     */
    private String scoreAlgorithmId;

    /**
     * 得分算法名称
     */
    private String scoreAlgorithmName;
}
