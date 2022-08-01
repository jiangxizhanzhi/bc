package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ElementFlowList implements Serializable {

	private List<ElementFlow> flows;
}
