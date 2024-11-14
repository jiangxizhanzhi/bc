package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 元素常用上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ElementCommonContext implements Serializable {

	/**
	 * 场景id
	 */
	private String sceneId;

	/**
	 * 元素id列表
	 */
	private String elementIds;

}
