package com.cyberpeace.rtks.res;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeTreeRes {

	/**
	 * 类别
	 */
	@ApiModelProperty("类别")
	private String category;

	/**
	 * 知识列表
	 */
	@ApiModelProperty("知识列表")
	private List<KnowledgeRes> knowledgeResList;

}