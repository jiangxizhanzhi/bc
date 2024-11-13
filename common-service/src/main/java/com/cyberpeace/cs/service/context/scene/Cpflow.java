package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.Map;

/**
 * CPflow
 *
 * @author SN
 * @date 2024/11/11
 */
public interface Cpflow extends Serializable {

	Map<String, ElementFlowList> getElements();
}
