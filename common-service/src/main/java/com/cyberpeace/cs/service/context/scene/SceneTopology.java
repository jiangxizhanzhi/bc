package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景拓扑结构
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneTopology implements Serializable {

	private SceneStatus scene;

	private List<SceneElement> elements;

}
