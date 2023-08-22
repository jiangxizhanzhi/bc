package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class OperationListQueryBo extends PatternBase {

	private String practiceSetId;

	private List<String> categoryIds;

	private Integer difficulty;

	private Boolean solved;

	private List<String> knowledgeList;

}
