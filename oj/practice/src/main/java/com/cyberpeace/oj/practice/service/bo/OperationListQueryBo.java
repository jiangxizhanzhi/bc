package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OperationListQueryBo extends PatternBase {

	private String practiceSetId;

	private List<String> categoryIds;

	private Integer difficulty;

	private Boolean solved;

	private List<String> knowledgeList;

	private String source;

}
