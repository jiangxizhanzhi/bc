package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑摘要数据
 */
@Data
public class TopoDigest implements Serializable {

	/**
	 * 拓扑中节点数
	 */
	private Integer nodeCounts;

	/**
	 * 拓扑中占用物理资源的节点数
	 */
	private Integer resourceOccupiedNums;

	private String topoId;
}
