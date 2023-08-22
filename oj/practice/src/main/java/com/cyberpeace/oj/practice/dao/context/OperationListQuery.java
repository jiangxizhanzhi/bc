package com.cyberpeace.oj.practice.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

@Data
public class OperationListQuery extends PatternBase {

	private List<String> resourceIds;

	private List<String> practiceSetIds;

	private List<String> categoryIds;

	private Integer difficulty;

	/**
	 * 知识点正则
	 */
	private String knowledgeExp;

}
