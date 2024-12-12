package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景元素
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
