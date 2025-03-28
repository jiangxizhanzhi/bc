package com.cyberpeace.cs.service.context.scene;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 拓扑元素
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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

	private Integer type;

	private String file;

	private String nodeCommand;
}
