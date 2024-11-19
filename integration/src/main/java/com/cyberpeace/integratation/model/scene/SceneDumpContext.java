package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 场景导出上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneDumpContext implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * env 路径
	 *
	 * @see String
	 */
	private String env_path;

	/**
	 * 图像路径
	 *
	 * @see String
	 */
	private String image_path;

	/**
	 * 单个图像路径
	 *
	 * @see String
	 */
	private String single_image_path;

	/**
	 * 场景数
	 *
	 * @see String
	 */
	private String scene_number;

	/**
	 * 禁用图像导出
	 *
	 * @see Boolean
	 */
	private Boolean disable_image_export;
}