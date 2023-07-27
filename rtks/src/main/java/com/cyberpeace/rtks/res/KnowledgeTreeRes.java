package com.cyberpeace.rtks.res;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeTreeRes implements Serializable {

	@ApiModelProperty("类别")
	private String category;

	@ApiModelProperty("知识列表")
	private List<KnowledgeRes> knowledgeResList;

}