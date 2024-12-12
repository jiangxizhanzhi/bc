package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 拓扑摘要数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TopoDigest implements Serializable {

	/**
	 * 拓扑中节点数
	 */
	private Integer nodeCounts;

	/**
	 * 拓扑中占用的授权节点数量
	 */
	private Integer resourceOccupiedNums;

	/**
	 * 拓扑id
	 */
	private String topoId;
}
