package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateListReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateListRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRoleRsp;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "红蓝对抗模板前台")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue_templates/")
public class RbTemplateController {

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<RbTemplateListRsp>> list(RbTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		// 默认时间排序
		listQuery.setSort("-cr_business_template.create_time");

		IPage<RbTemplateData> data = rbTemplateService.page(listQuery);

		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
				data.getRecords().stream().map(rbTemplateData -> rbTemplateData.getTopoId())
						.collect(Collectors.toList()));

		data.convert(rbTemplateData -> getRbTemplateRspByBaseInfoData(rbTemplateData,
				topologyNodesStats));

		return new BaseRsp(PageUtil.getListResult(data));
	}

	private RbTemplateListRsp getRbTemplateRspByBaseInfoData(RbTemplateData rbTemplateData,
			Map<String, TopoDigest> topologyNodesStats) {
		RbTemplateListRsp rbTemplateListRsp = new RbTemplateListRsp();

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(rbTemplateData);

		rbTemplateListRsp
				.setNodesCount(topologyNodesStats.get(rbTemplateData.getTopoId()).getNodeCounts());
		rbTemplateListRsp.setResourceId(rbTemplateData.getResourceId());
		rbTemplateListRsp.setCrEventInfo(baseInfoRsp);
		String roleString = rbTemplateData.getRoleConfig();

		JSONObject rolejsonObject = JsonUtil.toJsonObject(roleString, JSONObject.class);

		List<RbTemplateRoleRsp> rbTemplateRoleRsps = Lists.newArrayList();

		RbTemplateRoleRsp rbTemplateRoleRsp = new RbTemplateRoleRsp(
				CrRole.RbRoleGroupCode.ADMIN.getDesc(), CrRole.RbRoleGroupCode.ADMIN.getCode());
		rbTemplateRoleRsps.add(rbTemplateRoleRsp);

		RbTemplateRoleRsp rbTemplateRoleRsp1 = new RbTemplateRoleRsp(
				CrRole.RbRoleGroupCode.DIRECTOR.getDesc(),
				CrRole.RbRoleGroupCode.DIRECTOR.getCode());
		rbTemplateRoleRsps.add(rbTemplateRoleRsp1);

		RbTemplateRoleRsp rbTemplateRoleRsp2 = new RbTemplateRoleRsp(
				CrRole.RbRoleGroupCode.REFEREE.getDesc(), CrRole.RbRoleGroupCode.REFEREE.getCode());
		rbTemplateRoleRsps.add(rbTemplateRoleRsp2);

		RbTemplateRoleRsp rbTemplateRoleRsp3 = new RbTemplateRoleRsp(
				CrRole.RbRoleGroupCode.RED.getDesc(), CrRole.RbRoleGroupCode.RED.getCode());

		JSONArray redRoles = (JSONArray) rolejsonObject.get("red_roles");
		List<RbTemplateRoleRsp> redTemplateRoleRsps = Lists.newArrayList();
		for (int i = 0; i < redRoles.size(); i++) {

			RbTemplateRoleRsp redTemplateRole = new RbTemplateRoleRsp(
					redRoles.getJSONObject(i).get("name").toString(),
					CrRole.RbRoleGroupCode.RED.getCode());
			redTemplateRoleRsps.add(redTemplateRole);
		}
		rbTemplateRoleRsp3.setChildren(redTemplateRoleRsps);
		rbTemplateRoleRsps.add(rbTemplateRoleRsp3);

		RbTemplateRoleRsp rbTemplateRoleRsp4 = new RbTemplateRoleRsp(
				CrRole.RbRoleGroupCode.BLUE.getDesc(), CrRole.RbRoleGroupCode.BLUE.getCode());

		JSONArray blueRoles = (JSONArray) rolejsonObject.get("blue_roles");
		List<RbTemplateRoleRsp> blueTemplateRoleRsps = Lists.newArrayList();
		for (int i = 0; i < blueRoles.size(); i++) {

			RbTemplateRoleRsp blueTemplateRole = new RbTemplateRoleRsp(
					blueRoles.getJSONObject(i).get("name").toString(),
					CrRole.RbRoleGroupCode.BLUE.getCode());
			blueTemplateRoleRsps.add(blueTemplateRole);
		}
		rbTemplateRoleRsp4.setChildren(blueTemplateRoleRsps);
		rbTemplateRoleRsps.add(rbTemplateRoleRsp4);

		rbTemplateListRsp.setRoleInfo(rbTemplateRoleRsps);

		return rbTemplateListRsp;
	}

}
