package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalIndicatorLoadBo implements Serializable {

    private String evalIndicatorId;

    private String name;

    private String description;

    private Integer datatype;

    private Integer attribute;

    private Integer attributeValue;

    private EvalAlgorithmLoadBo aggregatingAlgorithm;

    private String aggregatingAlgorithmParams;

    private List<EvalAggregationLoadBo> aggregations;

}
