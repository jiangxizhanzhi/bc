package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑元素
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TopoElement implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 系统类型
	 */
	private String systemType;
}
