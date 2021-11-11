package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class OperationListQueryBo implements Serializable {

    private String practiceSetId;

    private List<String> categoryIds;

    private Integer difficulty;

    private String pattern;

    private Boolean solved;

}
