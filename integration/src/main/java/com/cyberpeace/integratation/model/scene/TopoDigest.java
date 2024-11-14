package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑摘要数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
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

	/**
	 * 总数CPU
	 */
	private Integer totalCpu;

	/**
	 * 总数磁盘
	 */
	private Integer totalDisk;

	/**
	 * 总数内存
	 */
	private Integer totalRam;

}
