package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.context.scene.ElementFlow;

/**
 * 元素流量列表
 *
 * @author SN
 * @date 2024/11/11
 */
public interface ElementFlowList extends Serializable {

	List<ElementFlow> getFlows();
}