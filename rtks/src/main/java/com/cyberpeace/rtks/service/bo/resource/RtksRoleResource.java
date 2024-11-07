package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

/**
 * RTKS 角色资源
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RtksRoleResource implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 水平
	 */
	private String level;

	/**
	 * 任务id
	 */
	private List<String> taskId = Lists.newArrayList();

	/**
	 * 知识id列表权重
	 */
	private List<IdAndWeight> knowledgeIdsWeights = Lists.newArrayList();

	/**
	 * 知识权重
	 */
	private Integer knowledgeWeight;

	/**
	 * 技能id列表权重
	 */
	private List<IdAndWeight> skillIdsWeights = Lists.newArrayList();
}
