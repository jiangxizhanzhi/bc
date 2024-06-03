package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.common.context.role.CrRoleNodesData;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.google.common.collect.Sets;

import lombok.Data;

@Service
public class SceneFilterServiceImpl implements SceneFilterService {

	private static final String TOPOLOGY = "topology";

	@Override
	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null || null == topoData) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		List<String> assetNodeIds = nodes.getAssetNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject(TOPOLOGY).getJSONArray("elements");
		// 过滤节点
		filterElements(topoData, elements, visibleNodeIds, assetNodeIds);
		// 过滤层节点
		JSONArray layerNodes = filterLayerNode(topoData, visibleNodeIds);
		// 移除失效连线
		removeInvalidConnections(topoData, elements);
		removeInvalidLayerConnections(topoData, visibleNodeIds);

		// remove empty areas
		JSONArray areas = topoData.getJSONObject(TOPOLOGY).getJSONArray("areas");
		JSONArray pieces = topoData.getJSONObject(TOPOLOGY).getJSONArray("pieces");
		filterAreas(areas, pieces, elements, layerNodes);
	}

	private void filterElements(JSONObject topoData, JSONArray elements,
			List<String> visibleNodeIds, List<String> assetNodeIds) {
		// empty topo
		if (elements.isEmpty()) {
			return;
		}
		JSONObject elementPorts = topoData.getJSONObject("plugins").getJSONObject("cpcloud")
				.getJSONObject("element_ports");
		int i = 0;
		while (i < elements.size()) {
			JSONObject element = elements.getJSONObject(i);
			String elementId = element.getString("id");
			Integer elementType = element.getInteger("type");
			if (!visibleNodeIds.contains(elementId)) {
				// remove invisible element
				elements.remove(i);
			} else {
				// 非资产节点去除接入方式
				// 去除终端的ports的access_modes
				if (!assetNodeIds.contains(elementId)
						&& elementType.equals(CrCommonConstants.TERMINAL_TYPE_CODE)) {
					JSONArray ports = element.getJSONArray("ports");
					for (int j = 0; j < ports.size(); j++) {
						JSONObject port = ports.getJSONObject(j);
						// 去掉 elements 的 access_modes
						port.put("access_modes", Lists.newArrayList());
						// 去掉 port 的 ip => 去掉重启重建按钮
						port.put("ip", null);
						// 去掉 plugins 的 access_modes
						String portId = port.getString("id");
						elementPorts.getJSONObject(portId).put("access_modes",
								Lists.newArrayList());
					}
				}
				i++;
			}
		}
	}

	/**
	 * 去除不可见的层节点
	 * 获取可见层节点的数据
	 * @param topoData       拓扑data
	 * @param visibleNodeIds visiblenodeid列表
	 * @return {@code JSONArray}
	 */
	private JSONArray filterLayerNode(JSONObject topoData, List<String> visibleNodeIds) {
		// 处理层节点，层节点在areas列表
		JSONArray areas = topoData.getJSONObject(TOPOLOGY).getJSONArray("areas");
		int k = 0;
		JSONArray layerNodes = new JSONArray();
		while (k < areas.size()) {
			JSONObject area = areas.getJSONObject(k);
			Integer type = area.getInteger("type");
			String id = area.getString("id");
			if (type == 1) {
				if (!visibleNodeIds.contains(id)) {
					areas.remove(k);
				} else {
					layerNodes.add(area);
					k++;
				}
			} else {
				k++;
			}
		}
		return layerNodes;
	}

	/**
	 * 去掉不含节点的 Block
	 * @param areas Blocks
	 * @param pieces Labels
	 * @param elements Nodes
	 */
	private void filterAreas(JSONArray areas, JSONArray pieces, JSONArray elements,
			JSONArray layerNodes) {
		int k = 0;
		while (k < areas.size()) {
			String areaPositionJson = areas.getJSONObject(k).getString("position2");
			Integer type = areas.getJSONObject(k).getInteger("type");
			Area area = JsonUtil.toJsonObject(areaPositionJson, Area.class);
			// 将层节点视作节点
			if (type != 1 && !area.contains(elements) && !area.contains(layerNodes)) {
				areas.remove(k);
				filterPieces(area, pieces);
			} else {
				k++;
			}
		}
	}

	/**
	 * 要去掉的Block里的Label也要去掉
	 */
	private void filterPieces(Area area, JSONArray pieces) {
		int i = 0;
		while (i < pieces.size()) {
			if (area.contains(pieces.get(i))) {
				pieces.remove(i);
			} else {
				i++;
			}
		}
	}

	/**
	 * 移除不可见的节点之后，需要额外移除节点间的连线
	 * @param topoData 拓扑数据
	 * @param elements 剩余的节点
	 */
	private void removeInvalidConnections(JSONObject topoData, JSONArray elements) {
		JSONArray connections = topoData.getJSONObject(TOPOLOGY).getJSONArray("connections");
		Set<String> existingPorts = Sets.newHashSet();
		// collect existing ports
		for (int i = 0; i < elements.size(); i++) {
			JSONObject element = elements.getJSONObject(i);
			JSONArray ports = element.getJSONArray("ports");
			if (ports == null) {
				continue;
			}
			for (int j = 0; j < ports.size(); j++) {
				String portId = ports.getJSONObject(j).getString("id");
				existingPorts.add(portId);
			}
		}
		int k = 0;
		while (k < connections.size()) {
			JSONObject connection = connections.getJSONObject(k);
			String source = connection.getString("source");
			String target = connection.getString("target");
			if (existingPorts.contains(source) && existingPorts.contains(target)) {
				// source and target both exist, valid connection
				k++;
			} else {
				// remove invalid connection
				connections.remove(k);
			}
		}
	}

	private void removeInvalidLayerConnections(JSONObject topoData, List<String> visibleNodeIds) {
		JSONArray connections = topoData.getJSONObject(TOPOLOGY).getJSONArray("node_connections");
		int i = 0;
		while (i < connections.size()) {
			JSONObject connection = connections.getJSONObject(i);
			String sourceId = connection.getString("source_node_id");
			String targetId = connection.getString("target_node_id");
			if (visibleNodeIds.contains(sourceId) && visibleNodeIds.contains(targetId)) {
				i++;
			} else {
				connections.remove(i);
			}
		}
	}

}

/**
 * Area 范围
 */
@Data
class Area {

	private Coordination origin;

	private Float width;

	private Float height;

	public Boolean contains(Coordination coordination) {
		return origin.getX() <= coordination.getX() && coordination.getX() <= origin.getX() + width
				&& origin.getY() <= coordination.getY()
				&& coordination.getY() <= origin.getY() + height;
	}

	public Boolean contains(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		String position = jsonObject.getString("position2");
		Coordination coordination;
		if (position.contains("origin")) {
			Area area = JsonUtil.toJsonObject(position, Area.class);
			coordination = area.getOrigin();
		} else {
			coordination = JsonUtil.toJsonObject(jsonObject.getString("position2"),
					Coordination.class);
		}
		return contains(coordination);
	}

	/**
	 * area 含有至少一个节点，不隐藏； 否则隐藏 area
	 */
	public Boolean contains(JSONArray elements) {
		for (Object element : elements) {
			if (contains(element)) {
				return true;
			}
		}
		return false;
	}
}

/**
 * 拓扑中的坐标
 */
@Data
class Coordination {

	private Float x;

	private Float y;
}