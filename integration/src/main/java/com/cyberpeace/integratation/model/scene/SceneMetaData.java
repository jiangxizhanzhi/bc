package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 场景元数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneMetaData implements Serializable {

	/**
	 * 业务数据编号
	 */
	private String businessInstanceId;

	/**
	 * 场景名称
	 */
	private String sceneName;

}
