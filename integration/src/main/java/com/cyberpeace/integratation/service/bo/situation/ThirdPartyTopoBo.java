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

	private List<ElementContext> networks;

	private List<ElementContext> nodes;

	private List<ConnectionContext> connections;

	public TopoContext convert() {
		TopoContext context = new TopoContext();
		context.setBaseInfo(this.baseInfo);
		context.setConnections(this.connections);
		context.setElements(Maps.newHashMap());
		this.networks.forEach(network -> {
			network.setElementType("network");
			network.setDeviceType("网络设备-外部网络");
			context.getElements().put(network.getElementId(), network);
		});
		this.nodes.forEach(node -> {
			node.setElementType("node");
			node.setDeviceType("操作系统-LINUX");
			context.getElements().put(node.getElementId(), node);
		});
		return context;
	}
}
