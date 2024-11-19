package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 元素常用上下文
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/14
 */
@Data
public class ElementCommonContext implements Serializable {

	/**
     * 场景id
     *
     * @see String
     */
	private String sceneId;

	/**
     * 元素id列表
     *
     * @see String
     */
	private String elementIds;

}
