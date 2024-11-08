package com.cyberpeace.cr.crbase.dao.entity;

import lombok.Data;

/**
 * 靶场模板公共信息类
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class BusinessTemplateData extends BusinessTemplate {

	/**
	 * 节点数量
	 */
	private Integer nodesNum;

	/**
	 * 资源占用数
	 */
	private Integer resourceOccupiedNums;

}
