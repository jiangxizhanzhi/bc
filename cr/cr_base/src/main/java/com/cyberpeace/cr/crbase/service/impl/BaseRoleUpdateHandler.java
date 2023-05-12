package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cs.controller.req.scene.RoleConfig;
import com.cyberpeace.cs.controller.req.scene.SceneUpdateReq;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.SceneDynamicService;
import com.google.common.collect.Maps;

/**
 * 动态更新场景时，更新角色
 * 当前只支持修改角色的资产，不支持增加、删除角色、角色改名
 *
 * @author SN
 * @date 2023/05/12
 */
@Service
public class BaseRoleUpdateHandler implements SceneDynamicService {

	private static Map<Integer, BaseRoleUpdateHandler> handlerMap = Maps.newHashMap();

	public static Map<Integer, BaseRoleUpdateHandler> getHandlerMap() {
		return handlerMap;
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
	 * @param businessInstanceId 业务实例id
	 * @param req                请求体
	 */
	public void updateRoles(String businessInstanceId, SceneUpdateReq req) {}

	@Override
	public void updateTraining(LocalScene localScene, SceneUpdateReq req) {
		BaseRoleUpdateHandler handler = getHandlerMap().get(localScene.getBusinessCode());
		if (null != handler) {
			handler.updateRoles(localScene.getBusinessInstanceId(), req);
		}
	}

	/**
	 * 在角色配置列表中查找角色，并判断是否修改
	 *
	 * @param role           角色
	 * @param roleConfigList 角色配置列表
	 * @param modified       修改角色列表
	 */
	public void filterModified(CrRole role, List<RoleConfig> roleConfigList,
			List<CrRole> modified) {
		for (RoleConfig config : roleConfigList) {
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
}
