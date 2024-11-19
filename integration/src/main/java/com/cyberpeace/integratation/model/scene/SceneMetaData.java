package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景元数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneMetaData implements Serializable {

	/**
	 * 业务数据编号
	 *
	 * @see String
	 */
	private String businessInstanceId;

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	private String sceneName;

}
