package com.cyberpeace.rtks.res;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识点树结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class KnowledgeTreeRes {

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("类别")
	private String category;

	/**
	 * 知识列表
	 *
	 * @see List<KnowledgeRes>
	 */
	@ApiModelProperty("知识列表")
	private List<KnowledgeRes> knowledgeResList;

}