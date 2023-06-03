package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.role.RoleUpdateConfig;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.google.common.collect.Maps;

/**
 * 更新角色
 *
 * @author SN
 * @date 2023/05/12
 */
public abstract class BaseCrSceneUpdateHandler {

	private static Map<Integer, BaseCrSceneUpdateHandler> handlerMap = Maps.newHashMap();

	public static BaseCrSceneUpdateHandler getHandler(Integer type) {
		return handlerMap.get(type);
	}

	protected Integer getCode() {
		return null;
	}

	protected void register() {
		if (null != getCode()) {
			handlerMap.put(getCode(), this);
		}
	}

	@PostConstruct
	protected void init() {
		register();
	}

	/**
	 * 检查权限
	 *
	 * @param businessInstanceId 业务实例id
	 */
	protected void checkPermission(String businessInstanceId) {}

	/**
	 * 更新角色
	 *
	 * @param roomId 业务实例id
	 * @param req                请求体
	 */
	public void updateRoles(String roomId, SceneUpdateReq req) {}

	/**
	 * 删除节点时，删除节点的用户资产和常用资产
	 *
	 * @param roomId 房间id
	 * @param nodeId 节点id
	 */
	public void deleteNode(String roomId, String nodeId) {}

	/**
	 * 在角色配置列表中查找角色，并判断是否修改
	 *
	 * @param role           角色
	 * @param roleConfigList 角色配置列表
	 * @param modified       修改角色列表
	 */
	protected void filterModified(CrRole role, List<RoleUpdateConfig> roleConfigList,
			List<CrRole> modified) {
		for (RoleUpdateConfig config : roleConfigList) {
			// identify role by name and type
			if (config.getName().equals(role.getName())) {
				String nodeJson = JsonUtil.toJsonString(config.getNodes());
				// 判断节点配置是否相同
				if (!role.getNodes().equals(nodeJson)) {
					role.setNodes(nodeJson);
					modified.add(role);
					break;
				}
			}
		}
	}

	/**
	 * 获取过滤后节点数据
	 * 去除指定id节点
	 *
	 * @param role   角色
	 * @param nodeId 节点id
	 * @return {@code String}
	 */
	protected String getFilteredNodesData(CrRole role, String nodeId) {
		CrRoleNodesData nodesData = JsonUtil.toJsonObject(role.getNodes(), CrRoleNodesData.class);
		nodesData.getAssetNodes().removeIf(node -> node.getId().equals(nodeId));
		nodesData.getVisibleNodes().removeIf(node -> node.getId().equals(nodeId));
		return JsonUtil.toJsonString(nodesData);
	}
}
