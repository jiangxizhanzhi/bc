package com.cyberpeace.cr.hld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.hld.controller.rsp.HldTopoAssetsRsp;
import com.cyberpeace.cr.hld.service.HldSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "HLD房间")
@Slf4j
@RestController
@RequestMapping("/cr/hld/")
public class HldUnAuthController {

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private HldSceneService hldSceneService;

	@ApiOperation("读取topo和角色资产")
	@GetMapping("/topoAssets")
	public BaseRsp<HldTopoAssetsRsp> getTopoAssets(
			@ApiParam(name = "resource_id", value = "HLD房间id", required = true) String resource_id) {

		// read scene data
		JSONObject sceneData = hldSceneService.viewSceneNoAuth(resource_id);
		if (sceneData == null) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		HldTopoAssetsRsp rsp = new HldTopoAssetsRsp();
		rsp.setSceneData(sceneData);

		// read role assets
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(resource_id);
		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole role : crRoles) {
			if (role.isHldStudent()) {
				CrRoleConfig config = JsonUtil.toJsonObject(role.getNodes(), CrRoleConfig.class);
				config.setName(role.getName());
				config.setId(role.getResourceId());

				if (role.isHldBlueStudent()) {
					rsp.getBlueRole().add(config);
				} else {
					rsp.getRedRoles().add(config);
				}
			}
		}
		return new BaseRsp<>(rsp);
	}
}
