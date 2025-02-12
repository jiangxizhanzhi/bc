package com.cyberpeace.cr.evaluation.controller.req;

import lombok.Data;

@Data
public class DeviceCommandModelReq {

	/**
	 * 角色
	 */
	private Integer role;
	/**
	 * 一级分类
	 */
	private Integer mainCategory;
	/**
	 * 二级分类
	 */
	private Integer subCategory;
	/**
	 * 三级分类
	 */
	private Integer thirdCategory;
	/**
	 * 镜像类型：vm，container,simple, entity
	 */
	private String imageType;
	/**
	 * 业务标签,1:终端防护靶标,2:路由交换靶标,3:边界安全防护设备靶标,4:流量分析靶标,5:应用服务靶标
	 */
	private Integer businessTag;
	/**
	 * 设备品牌
	 */
	private String brand;
}
