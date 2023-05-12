package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
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

	@Autowired
	private RtksRelatedService rtksRelatedService;

	@ApiOperation("前台列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
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

		// get attack engage relation
		List<String> businessTemplateIds = data.getRecords().stream()
				.map(RbTemplateData::getBusinessTemplateId).collect(Collectors.toList());
		Map<String, List<NameCount>> attackRelCount = rtksRelatedService
				.countAttackRel(businessTemplateIds);
		Map<String, List<NameCount>> engageRelCount = rtksRelatedService
				.countEngageRel(businessTemplateIds);

		List<RbTemplateListRsp> rspList = Lists.newArrayList();
		// set attack engage relation statistics
		for (RbTemplateData record : data.getRecords()) {
			RbTemplateListRsp rspElement = getRbTemplateRspByBaseInfoData(record,
					topologyNodesStats);
			rspElement
					.setIsAttackRelated(attackRelCount.containsKey(record.getBusinessTemplateId()));
			rspElement.setAttackCount(attackRelCount.getOrDefault(record.getBusinessTemplateId(),
					Lists.newArrayList()));
			rspElement
					.setIsEngageRelated(engageRelCount.containsKey(record.getBusinessTemplateId()));
			rspElement.setEngageCount(engageRelCount.getOrDefault(record.getBusinessTemplateId(),
					Lists.newArrayList()));

			rspList.add(rspElement);
		}

		ListRsp res = PageUtil.getListResult(data);
		// 替换返回数据
		res.setList(rspList);

		return new BaseRsp(res);
	}

	private RbTemplateListRsp getRbTemplateRspByBaseInfoData(RbTemplateData rbTemplateData,
			Map<String, TopoDigest> topologyNodesStats) {
		RbTemplateListRsp rbTemplateListRsp = new RbTemplateListRsp();

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(rbTemplateData);

		rbTemplateListRsp
				.setNodesCount(topologyNodesStats.get(rbTemplateData.getTopoId()).getNodeCounts());
		rbTemplateListRsp.setResourceOccupiedNums(
				topologyNodesStats.get(rbTemplateData.getTopoId()).getResourceOccupiedNums());
		rbTemplateListRsp.setResourceId(rbTemplateData.getResourceId());
		rbTemplateListRsp.setBusinessTemplateId(rbTemplateData.getBusinessTemplateId());
		rbTemplateListRsp.setCrEventInfo(baseInfoRsp);
		String roleString = rbTemplateData.getRoleConfig();

		JSONObject rolejsonObject = JsonUtil.toJsonObject(roleString, JSONObject.class);

		List<RbTemplateRoleRsp> rbTemplateRoleRsps = Lists.newArrayList();

		rbTemplateRoleRsps.add(new RbTemplateRoleRsp(CrRole.RbRoleGroup.ADMIN));

		rbTemplateRoleRsps.add(new RbTemplateRoleRsp(CrRole.RbRoleGroup.DIRECTOR));

		rbTemplateRoleRsps.add(new RbTemplateRoleRsp(CrRole.RbRoleGroup.REFEREE));

		RbTemplateRoleRsp rbTemplateRoleRsp3 = new RbTemplateRoleRsp(CrRole.RbRoleGroup.RED);

		JSONArray redRoles = (JSONArray) rolejsonObject.get("red_roles");
		List<RbTemplateRoleRsp> redTemplateRoleRsps = Lists.newArrayList();
		for (int i = 0; i < redRoles.size(); i++) {

			RbTemplateRoleRsp redTemplateRole = new RbTemplateRoleRsp(CrRole.RbRoleGroup.RED,
					redRoles.getJSONObject(i).get("name").toString());
			redTemplateRoleRsps.add(redTemplateRole);
		}
		rbTemplateRoleRsp3.setChildren(redTemplateRoleRsps);
		rbTemplateRoleRsps.add(rbTemplateRoleRsp3);

		RbTemplateRoleRsp rbTemplateRoleRsp4 = new RbTemplateRoleRsp(CrRole.RbRoleGroup.BLUE);

		JSONArray blueRoles = (JSONArray) rolejsonObject.get("blue_roles");
		List<RbTemplateRoleRsp> blueTemplateRoleRsps = Lists.newArrayList();
		for (int i = 0; i < blueRoles.size(); i++) {

			RbTemplateRoleRsp blueTemplateRole = new RbTemplateRoleRsp(CrRole.RbRoleGroup.BLUE,
					blueRoles.getJSONObject(i).get("name").toString());
			blueTemplateRoleRsps.add(blueTemplateRole);
		}
		rbTemplateRoleRsp4.setChildren(blueTemplateRoleRsps);
		rbTemplateRoleRsps.add(rbTemplateRoleRsp4);

		rbTemplateListRsp.setRoleInfo(rbTemplateRoleRsps);

		return rbTemplateListRsp;
	}

}
