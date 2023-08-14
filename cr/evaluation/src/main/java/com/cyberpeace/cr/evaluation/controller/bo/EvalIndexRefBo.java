package com.cyberpeace.cr.evaluation.controller.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalIndexRefBo implements Serializable {

    private String resourceId;

    private String name;

    private String algorithmId;

    private String algorithmParams;

    private Integer level;

    private String nextIds;

    private Integer score;

    private List<EvalIndexRefBo> children;

    private String variableRefs;

}
