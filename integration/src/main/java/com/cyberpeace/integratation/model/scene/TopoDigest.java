package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑摘要数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TopoDigest implements Serializable {

	/**
	 * 拓扑中节点数
	 *
	 * @see Integer
	 */
	private Integer nodeCounts;

	/**
	 * 拓扑中占用的授权节点数量
	 *
	 * @see Integer
	 */
	private Integer resourceOccupiedNums;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private String topoId;

	/**
	 * 总数CPU
	 *
	 * @see Integer
	 */
	private Integer totalCpu;

	/**
	 * 总数磁盘
	 *
	 * @see Integer
	 */
	private Integer totalDisk;

	/**
	 * 总数内存
	 *
	 * @see Integer
	 */
	private Integer totalRam;

}
