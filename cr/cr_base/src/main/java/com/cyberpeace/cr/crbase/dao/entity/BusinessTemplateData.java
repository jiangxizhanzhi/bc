package com.cyberpeace.cr.crbase.dao.entity;

import lombok.Data;

/**
 * 靶场模板公共信息类
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BusinessTemplate
 * @since 1.0.0
 */
@Data
public class BusinessTemplateData extends BusinessTemplate {

	/**
	 * 节点数量
	 *
	 * @see Integer
	 */
	private Integer nodesNum;

	/**
	 * 资源占用数
	 *
	 * @see Integer
	 */
	private Integer resourceOccupiedNums;

}
