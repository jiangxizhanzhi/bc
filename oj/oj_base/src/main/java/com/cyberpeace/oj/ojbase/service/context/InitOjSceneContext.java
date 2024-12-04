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
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitOjSceneContext implements Serializable {

	/**
	 * 业务编号 一般指 资源编号
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 是否为前台启动场景
	 *
	 * @see Boolean
	 */
	private Boolean fromWeb;

	/**
	 * 场景共享类型
	 *
	 * @see Integer
	 */
	private Integer sharedType;

	/**
	 * 用户编号
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 是否为动态flag
	 *
	 * @see Integer
	 */
	private Integer dynamicFlag;

	/**
	 * flag数据 为动态则不使用该值
	 *
	 * @see String
	 */
	private String flags;

}
