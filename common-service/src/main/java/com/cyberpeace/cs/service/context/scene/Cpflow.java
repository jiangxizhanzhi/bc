package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.Map;

public interface Cpflow extends Serializable {

	Map<String, ElementFlowList> getElements();
}
