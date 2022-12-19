package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.ConnectionContext;
import com.cyberpeace.integratation.model.situation.ElementContext;
import com.cyberpeace.integratation.model.situation.SceneContext;
import com.cyberpeace.integratation.model.situation.TopoContext;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class ThirdPartyTopoBo implements Serializable {

	private SceneContext baseInfo;

	private Map<String, String> areas = Maps.newHashMap();

	private Map<String, ElementContext> networks;

	private Map<String, ElementContext> nodes;

	private List<ConnectionContext> connections;

	public TopoContext convert() {
		TopoContext context = new TopoContext();
		context.setBaseInfo(this.baseInfo);
		context.setConnections(this.connections);
		context.setElements(Maps.newHashMap());
		this.networks.forEach((networkId, network) -> {
			network.setElementType("network");
			context.getElements().put(networkId, network);
		});
		this.nodes.forEach((nodeId, node) -> {
			node.setElementType("node");
			context.getElements().put(nodeId, node);
		});
		return context;
	}
}
