package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实操参考数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationReferenceData implements Serializable {

	/**
	 * 场景类型
	 */
	private Integer sceneType;

	/**
	 * 创建用户
	 */
	private String createUser;

	/**
	 * 是 Web
	 */
	private Boolean isWeb;

	/**
	 * 场景名称
	 */
	private String sceneName;

	/**
	 * 应用程序资源id
	 */
	private String appResourceId;

	/**
	 * 内容
	 */
	private String content;

}
