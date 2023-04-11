package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalCollectionLoadBo implements Serializable {

    private String name;

    private EvalAlgorithmLoadBo formatAlgorithm;

    private String formatAlgorithmParams;

    private EvalAlgorithmLoadBo detectionAlgorithm;

    private String detectionAlgorithmParams;

    private EvalAlgorithmLoadBo scoreAlgorithm;

    private String scoreAlgorithmParams;
}
