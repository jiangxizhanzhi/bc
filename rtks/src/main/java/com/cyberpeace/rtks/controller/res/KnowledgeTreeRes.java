package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识树结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class KnowledgeTreeRes implements Serializable {

	/**
	 * 类别
	 */
	@ApiModelProperty("类别")
	private String category;

	/**
	 * 知识结果列表
	 */
	@ApiModelProperty("知识列表")
	private List<KnowledgeRes> knowledgeResList;

}