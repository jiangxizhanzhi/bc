package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalAlgorithmLoadBo implements Serializable {
    private String name;

    private String description;

    private Integer type;

    private Integer algorithmLanguage;

    private String code;

    private String params;

    private Integer paramsNum;
}
