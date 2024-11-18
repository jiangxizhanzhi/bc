package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑元素
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TopoElement implements Serializable {

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
	 * 系统类型
	 *
	 * @see String
	 */
	private String systemType;
}
