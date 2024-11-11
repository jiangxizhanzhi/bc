package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 场景转 储上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SceneDumpContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * env 路径
	 */
	private String env_path;

	/**
	 * 图像路径
	 */
	private String image_path;

	/**
	 * 单个图像路径
	 */
	private String single_image_path;

	/**
	 * 场景数
	 */
	private String scene_number;

	/**
	 * 禁用图像导出
	 */
	private Boolean disable_image_export;
}