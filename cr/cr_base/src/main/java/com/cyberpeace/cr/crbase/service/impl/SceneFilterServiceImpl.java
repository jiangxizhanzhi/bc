package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;

import lombok.Data;

@Service
public class SceneFilterServiceImpl implements SceneFilterService {

	@Override
	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null || null == topoData) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		List<String> assetNodeIds = nodes.getAssetNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject("topology").getJSONArray("elements");

		filterElements(topoData, elements, visibleNodeIds, assetNodeIds);

		// remove empty areas
		JSONArray areas = topoData.getJSONObject("topology").getJSONArray("areas");
		JSONArray pieces = topoData.getJSONObject("topology").getJSONArray("pieces");
		filterAreas(areas, pieces, elements);
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
	 * 去掉不含节点的 Block
	 * @param areas Blocks
	 * @param pieces Labels
	 * @param elements Nodes
	 */
	private void filterAreas(JSONArray areas, JSONArray pieces, JSONArray elements) {
		int k = 0;
		while (k < areas.size()) {
			String areaPositionJson = areas.getJSONObject(k).getString("position2");
			Area area = JsonUtil.toJsonObject(areaPositionJson, Area.class);
			if (!area.contains(elements)) {
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
		Coordination coordination = JsonUtil.toJsonObject(jsonObject.getString("position2"),
				Coordination.class);
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