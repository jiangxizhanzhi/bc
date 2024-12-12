package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点流量查询体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ElementFlowListContext implements Serializable {

	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 交互式类型
	 */
	private Integer interactiveType;

	/**
	 * 分页
	 */
	private Page page;

}
