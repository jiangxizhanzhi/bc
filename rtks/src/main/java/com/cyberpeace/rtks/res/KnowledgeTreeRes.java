package com.cyberpeace.rtks.res;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeTreeRes {

	@ApiModelProperty("类别")
	private String category;

	@ApiModelProperty("知识列表")
	private List<KnowledgeRes> knowledgeResList;

}