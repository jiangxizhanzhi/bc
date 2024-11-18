package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * 节点流量查询体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ElementFlowListContext implements Serializable {

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	private String mpSceneId;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private String topoId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	private String nodeId;

	/**
	 * 交互式类型
	 *
	 * @see Integer
	 */
	private Integer interactiveType;

	/**
	 * 分页
	 *
	 * @see Page
	 */
	private Page page;

}
