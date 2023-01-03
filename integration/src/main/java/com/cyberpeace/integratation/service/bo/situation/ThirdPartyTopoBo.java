package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.*;
import com.google.common.collect.Lists;
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

	public TopoContext setAreas(TopoContext topoContext) {

		topoContext.setAreas(Maps.newHashMap());

		this.networks.forEach(network -> {

			AreaContext areaContext = new AreaContext();
			String areaId = network.getElementId() + "area";
			areaContext.setAreaId(areaId);
			areaContext.setHierarchy(network.getHierarchy() + 1);
			areaContext.setName(network.getName() + "区域");

			if (network.getChild().size() >= 2) {
				List<Float> xList = Lists.newArrayList();
				List<Float> yList = Lists.newArrayList();

				network.getChild().forEach(elementId -> {
					xList.add(topoContext.getElements().get(elementId).getPosition().getX());
					yList.add(topoContext.getElements().get(elementId).getPosition().getY());
				});
				areaContext.setScope(getAreaScope(xList, yList));
				topoContext.getAreas().put(areaId, areaContext);
			}
		});

		return topoContext;

	}

	private List<PositionContext> getAreaScope(List<Float> xList, List<Float> yList) {

		// 获取x、y坐标的极值
		Float xMax = 0.0f;
		Float xMin = 0.0f;
		Float yMax = 0.0f;
		Float yMin = 0.0f;

		if (!xList.isEmpty() && !yList.isEmpty()) {
			xMax = Collections.max(xList);
			xMin = Collections.min(xList);
			yMax = Collections.max(yList);
			yMin = Collections.min(yList);
		}

		// 构造拓扑最大坐标范围
		List<PositionContext> sceneScope = Lists.newArrayList();
		sceneScope.add(new PositionContext(xMin, yMin));
		sceneScope.add(new PositionContext(xMin, yMax));
		sceneScope.add(new PositionContext(xMax, yMax));
		sceneScope.add(new PositionContext(xMax, yMin));

		return sceneScope;

	}
}
