package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalAggregationLoadBo implements Serializable {
    private String name;

    private EvalAlgorithmLoadBo aggregatingAlgorithm;

    private String aggregatingAlgorithmParams;

    private List<EvalCollectionLoadBo> collections;
}
