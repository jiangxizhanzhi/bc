package com.cyberpeace.oj.ojbase.service.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 初始化OJ场景上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitOjSceneContext implements Serializable {

	/**
	 * 业务编号 一般指 资源编号
	 */
	private String businessId;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 是否为前台启动场景
	 */
	private Boolean fromWeb;

	/**
	 * 场景共享类型
	 */
	private Integer sharedType;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 是否为动态flag
	 */
	private Integer dynamicFlag;

	/**
	 * flag数据 为动态则不使用该值
	 */
	private String flags;

}
