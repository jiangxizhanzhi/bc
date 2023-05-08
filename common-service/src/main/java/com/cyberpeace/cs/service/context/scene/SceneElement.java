package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景元素
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneElement implements Serializable {

	private String id;

	private String name;

	private List<Port> ports;
}
