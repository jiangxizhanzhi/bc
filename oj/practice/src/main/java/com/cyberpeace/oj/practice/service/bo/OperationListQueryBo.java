package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 实操列表查询Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class OperationListQueryBo extends PatternBase {

	/**
	 * 练习设置id
	 */
	private String practiceSetId;

	/**
	 * 类别id列表
	 */
	private List<String> categoryIds;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 解决
	 */
	private Boolean solved;

	/**
	 * 知识点列表
	 */
	private List<String> knowledgeList;

}
