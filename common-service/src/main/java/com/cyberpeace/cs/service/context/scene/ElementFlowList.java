package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.context.scene.ElementFlow;

public interface ElementFlowList extends Serializable {

	List<ElementFlow> getFlows();
}