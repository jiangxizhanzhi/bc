package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景元素
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneElement implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 港口
	 *
	 * @see List<Port>
	 */
	private List<Port> ports;
}
