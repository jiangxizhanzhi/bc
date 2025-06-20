package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 实操参考数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationReferenceData implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 场景类型
	 *
	 * @see Integer
	 */
	private Integer sceneType;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	private String createUser;

	/**
	 * 是 Web
	 *
	 * @see Boolean
	 */
	private Boolean isWeb;

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	private String sceneName;

	/**
	 * 应用程序资源id
	 *
	 * @see String
	 */
	private String appResourceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

}
