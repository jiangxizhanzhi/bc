package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

/**
 * RTKS岗位资源
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RtksRoleResource implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 水平
	 *
	 * @see String
	 */
	private String level;

	/**
	 * 任务id
	 *
	 * @see List<String>
	 */
	private List<String> taskId = Lists.newArrayList();

	/**
	 * 知识点id列表权重
	 *
	 * @see List<IdAndWeight>
	 */
	private List<IdAndWeight> knowledgeIdsWeights = Lists.newArrayList();

	/**
	 * 知识点权重
	 *
	 * @see Integer
	 */
	private Integer knowledgeWeight;

	/**
	 * 技能id列表权重
	 *
	 * @see List<IdAndWeight>
	 */
	private List<IdAndWeight> skillIdsWeights = Lists.newArrayList();
}
