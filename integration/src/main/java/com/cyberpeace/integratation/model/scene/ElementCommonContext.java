package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 元素通用上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
