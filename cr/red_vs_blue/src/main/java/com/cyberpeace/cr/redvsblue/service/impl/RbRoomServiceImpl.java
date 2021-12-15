package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements RbRoomService {

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public List<RbRoom> getByIds(List<String> roomIds) {
		if (roomIds.size() == 0) {
			return Lists.newArrayList();
		}
		return rbRoomMapper.selectBatchIds(roomIds);
	}

	@Override
	public List<RbRoom> getAll() {
		return rbRoomMapper.selectAll();
	}

	@Override
	public IPage<RbRoomData> list(ListQuery listQuery) {
		return null;
	}

	@Override
	public Integer countAll() {
		return rbRoomMapper.countAll();
	}

	@Override
	public RbRoom create(RbRoomContext rbRoomContext) {
		String userId = UserInfoContext.getUserContext().getUserId();

		RbTemplate template = rbTemplateService.getById(rbRoomContext.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException(rbRoomContext.getTemplate());
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		RbRoom rbRoom = new RbRoom(rbRoomContext, template);
		rbRoom.setCreateUserId(userId);
		rbRoom.setCopyedTopoId(remoteTopoService.copyTopo(businessTemplate.getTopoId()));
		rbRoom.setTemplateName(businessTemplate.getName());
		rbRoom.setTrainingStatus(RbRoom.TrainingStatusCode.UNOPENED.getCode());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(rbRoom);
				List<CrRole> crRoles = getCrRoleByRbTemplate(template);

				List<RoomRole> users = rbRoomContext.getRoleConfig();
				int index = 0;

				for (CrRole crRole : crRoles) {
					if (users.get(index).getUsers().size() == 0) {
						index += 2;
					}
					crRole.setUserIds(JsonUtil.toJsonString(users.get(index++).getUsers()));
					crRole.setRoomId(rbRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());

					crRoleService.save(crRole);

				}

			}

		});

		return rbRoom;
	}

	@Override
	public RbRoomData getRbRoomDataByRoomId(String resourceId) {
		RbRoomData teamRoomData = rbRoomMapper.getRbRoomDataByRoomId(resourceId);
		if (teamRoomData == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		return teamRoomData;
	}

	private List<CrRole> getCrRoleByRbTemplate(RbTemplate teamTemplateData) {
		List<CrRole> crRoleList = Lists.newArrayList();

		String roleString = teamTemplateData.getRoleConfig();

		JSONObject rolejsonObject = JsonUtil.toJsonObject(roleString, JSONObject.class);

		CrRole rbTemplateRoleRsp = new CrRole(CrRole.RbRoleGroupCode.ADMIN.getRole(),
				CrRole.RbRoleGroupCode.ADMIN.getCode());
		crRoleList.add(rbTemplateRoleRsp);

		CrRole rbTemplateRoleRsp1 = new CrRole(CrRole.RbRoleGroupCode.DIRECTOR.getRole(),
				CrRole.RbRoleGroupCode.DIRECTOR.getCode());
		crRoleList.add(rbTemplateRoleRsp1);

		CrRole rbTemplateRoleRsp2 = new CrRole(CrRole.RbRoleGroupCode.REFEREE.getRole(),
				CrRole.RbRoleGroupCode.REFEREE.getCode());
		crRoleList.add(rbTemplateRoleRsp2);

		JSONArray redRoles = (JSONArray) rolejsonObject.get("redRoles");
		for (int i = 0; i < redRoles.size(); i++) {

			CrRole redTemplateRole = new CrRole(redRoles.getJSONObject(i).get("name").toString(),
					CrRole.RbRoleGroupCode.RED.getCode());
			redTemplateRole
					.setMissionScheme(redRoles.getJSONObject(i).get("missionsScheme").toString());
			redTemplateRole.setNodes(redRoles.getJSONObject(i).get("assetNodes").toString());

			crRoleList.add(redTemplateRole);
		}

		JSONArray blueRoles = (JSONArray) rolejsonObject.get("blueRoles");
		for (int i = 0; i < redRoles.size(); i++) {

			CrRole blueTemplateRole = new CrRole(blueRoles.getJSONObject(i).get("name").toString(),
					CrRole.RbRoleGroupCode.BLUE.getCode());
			blueTemplateRole
					.setMissionScheme(redRoles.getJSONObject(i).get("missionsScheme").toString());
			blueTemplateRole.setNodes(redRoles.getJSONObject(i).get("assetNodes").toString());

			crRoleList.add(blueTemplateRole);
		}

		return crRoleList;
	}

}
