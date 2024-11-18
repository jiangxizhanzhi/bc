package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;

/**
 * 评估收集上下文响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalCollectionContextRsp implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 格式算法id
     *
     * @see String
     */
    private String formatAlgorithmId;

    /**
     * 格式算法名称
     *
     * @see String
     */
    private String formatAlgorithmName;

    /**
     * 检波算法id
     *
     * @see String
     */
    private String detectionAlgorithmId;

    /**
     * 检波算法名称
     *
     * @see String
     */
    private String detectionAlgorithmName;

    /**
     * 得分算法id
     *
     * @see String
     */
    private String scoreAlgorithmId;

    /**
     * 得分算法名称
     *
     * @see String
     */
    private String scoreAlgorithmName;
}
