package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 20/7/2022 下午3:55
 */
@Data
public class EvalCollectionContextRsp implements Serializable {

    private String resourceId;

    private String name;

    private String formatAlgorithmId;

    private String formatAlgorithmName;

    private String detectionAlgorithmId;

    private String detectionAlgorithmName;

    private String scoreAlgorithmId;

    private String scoreAlgorithmName;
}
