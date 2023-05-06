package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 拓扑结构
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class Topology implements Serializable {

	private List<TopoElement> elements;
}
