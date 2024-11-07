package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景元素
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SceneElement implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 港口
	 */
	private List<Port> ports;
}
