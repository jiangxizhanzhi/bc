package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑元素
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TopoElement implements Serializable {

	private String id;

	private String name;

	private String systemType;
}
