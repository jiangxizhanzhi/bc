package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.ColorMission;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.cr.redvsblue.service.context.room.DashboardInfo;
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
	private MissionService missionService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public Integer getTrainingStatus(String roomId) {
		RbRoomData teamRoomdata = getRbRoomDataByRoomId(roomId);

		return teamRoomdata.getTrainingStatus();
	}

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
		String userId = UserInfoContext.getUserContext().getUserId();

		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));

		IPage<RbRoomData> data = rbRoomMapper.listRbRoom(page, listQuery);

		return data;
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
				List<CrRole> crRoles = getCrRoleByRbTemplate(template, rbRoom.getResourceId());

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
		RbRoomData rbRoomData = rbRoomMapper.getRbRoomDataByRoomId(resourceId);
		if (rbRoomData == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		return rbRoomData;
	}

	@Override
	public DashboardInfo getDashboardInfo(String resourceId) {
		// todo
		return null;
	}

	@Override
	public void updateRbRoom(RbRoomContext rbRoomContext, RbRoom rbRoom) {
		if (!StringUtils.isBlank(rbRoomContext.getName())) {
			rbRoom.setName(rbRoomContext.getName());
		}
		if (rbRoomContext.getCategory() != null) {
			rbRoom.setSubType(rbRoomContext.getCategory());
		}
		if (!StringUtils.isBlank(rbRoomContext.getDescription())) {
			rbRoom.setDescription(rbRoomContext.getDescription());
		}
		if (rbRoomContext.getPreSkill() != null && rbRoomContext.getPreSkill().size() > 0) {
			rbRoom.setPreSkill(JsonUtil.toJsonString(rbRoomContext.getPreSkill()));
		}
		if (rbRoomContext.getTrainSkill() != null && rbRoomContext.getTrainSkill().size() > 0) {
			rbRoom.setTrainSkill(JsonUtil.toJsonString(rbRoomContext.getTrainSkill()));
		}
		if (rbRoomContext.getStartTime() != null) {
			rbRoom.setStartTime(rbRoomContext.getStartTime());
		}
		if (rbRoomContext.getEndTime() != null) {
			rbRoom.setEndTime(rbRoomContext.getEndTime());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(rbRoom);
				if (rbRoomContext.getRoleConfig() != null
						&& rbRoomContext.getRoleConfig().size() > 0) {

					Map<String, RoomRole> roomRoleMap = rbRoomContext.getRoleConfig().stream()
							.collect(Collectors.toMap(RoomRole::getResourceId, bo -> bo));

					RoleQuery roleQuery = new RoleQuery();
					roleQuery.setRoomId(rbRoom.getResourceId());
					List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

					crRoleList.forEach(crRole -> {
						List<String> users = roomRoleMap.get(crRole.getResourceId()).getUsers();
						crRole.setUserIds(JsonUtil.toJsonString(users));
						crRoleService.updateById(crRole);

					});
				}
			}
		});
	}

	private void permissionCheck(String roomId, String userId, Integer roleCode) {
		RbRoom rbRoom = getById(roomId);
		if (rbRoom == null) {
			log.error("failed to get TeamRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!crRole.getRoleGroup().equals(roleCode)) {
			log.error("user does not have TeamRoom Permission！roomId=[{}], userId=[{}], role=[{}]",
					roomId, userId,
					EnumUtils.getEnumDescByCode(roleCode, CrRole.TeamRoleGroupCode.class));
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	@Override
	public void checkDirectorPermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.RbRoleGroupCode.DIRECTOR.getCode());
	}

	@Override
	public void checkRoomOpening(String roomId) {
		RbRoom rbRoom = getById(roomId);
		// 未开始，新增local_scene数据
		if (rbRoom.isUnopened()) {
			log.error("RedVsBlueTrain not begin! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);

		}
		if (rbRoom.isEnded()) {
			log.error("RedVsBlueTrain is over! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);

		}
	}

	private List<CrRole> getCrRoleByRbTemplate(RbTemplate rbTemplateData, String roomId) {
		List<CrRole> crRoleList = Lists.newArrayList();

		// 获取红蓝的角色组
		String roleString = rbTemplateData.getRoleConfig();

		CrRole rbTemplateRoleRsp = new CrRole(CrRole.RbRoleGroupCode.ADMIN.getDesc(),
				CrRole.RbRoleGroupCode.ADMIN.getCode());
		crRoleList.add(rbTemplateRoleRsp);

		CrRole rbTemplateRoleRsp1 = new CrRole(CrRole.RbRoleGroupCode.DIRECTOR.getDesc(),
				CrRole.RbRoleGroupCode.DIRECTOR.getCode());
		crRoleList.add(rbTemplateRoleRsp1);

		CrRole rbTemplateRoleRsp2 = new CrRole(CrRole.RbRoleGroupCode.REFEREE.getDesc(),
				CrRole.RbRoleGroupCode.REFEREE.getCode());
		crRoleList.add(rbTemplateRoleRsp2);

		// 获取红队的角色任务
		JSONObject rolejsonObject = JsonUtil.toJsonObject(roleString, JSONObject.class);
		JSONArray redRoles = (JSONArray) rolejsonObject.get("redRoles");
		for (int i = 0; i < redRoles.size(); i++) {

			CrRole crRole = getRoleByJson(roomId, i, redRoles,
					CrRole.RbRoleGroupCode.RED.getCode());

			crRoleList.add(crRole);
		}

		// 获取蓝队的角色任务
		JSONArray blueRoles = (JSONArray) rolejsonObject.get("blueRoles");
		for (int i = 0; i < blueRoles.size(); i++) {

			CrRole crRole = getRoleByJson(roomId, i, blueRoles,
					CrRole.RbRoleGroupCode.BLUE.getCode());

			crRoleList.add(crRole);

		}

		return crRoleList;
	}

	private CrRole getRoleByJson(String roomId, int index, JSONArray redRoles, Integer code) {
		JSONObject rolesJSONObject = redRoles.getJSONObject(index);

		CrRoleNodesData nodesData = new CrRoleNodesData();

		String assetNodes = rolesJSONObject.get("asset_nodes").toString();
		nodesData.setAssetNodes(JsonUtil.toJsonObjectArray(assetNodes, CrRoleNode.class));

		String visibleNodes = rolesJSONObject.get("visible_nodes").toString();
		nodesData.setVisibleNodes(JsonUtil.toJsonObjectArray(visibleNodes, CrRoleNode.class));

		CrRole crRole = new CrRole(rolesJSONObject.get("name").toString(), code);

		crRole.setMissionScheme(
				getNewRoomScheme(rolesJSONObject.get("missions_scheme").toString(), roomId));
		crRole.setNodes(JsonUtil.toJsonString(nodesData));

		return crRole;
	}

	/** 根据模板的任务id，复制任务并获取新的id，返回新的json字符串
	 * @param missionsScheme
	 * @return
	 */
	private String getNewRoomScheme(String missionsScheme, String roomId) {
		ColorMission<AddTemplateMission> colorMission = JSON.parseObject(missionsScheme,
				new TypeReference<ColorMission<AddTemplateMission>>() {});
		if (null == colorMission || null == colorMission.getSchemeData()) {
			return "[]";
		}

		List<MissionStageScheme> missionStageSchemes = Lists.newArrayList();
		List<String> originMissionIds = Lists.newArrayList();
		// 遍历每个阶段
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : colorMission.getSchemeData()) {

			List<AddTemplateMission> missions = schemeDatum.getMissions();
			// 遍历每个任务
			for (AddTemplateMission mission : missions) {
				originMissionIds.add(mission.getResourceId());
			}
		}

		Map<String, String> originMissionMaps = missionService.copyScheme(originMissionIds,
				UserInfoContext.getUserContext().getUserId(), roomId);

		// 遍历每个阶段设置新的任务id
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : colorMission.getSchemeData()) {
			MissionStageScheme missionStageScheme = new MissionStageScheme();

			List<String> targetMissionIds = Lists.newArrayList();

			// 遍历每个任务设置新的任务id
			for (AddTemplateMission mission : schemeDatum.getMissions()) {
				targetMissionIds.add(originMissionMaps.getOrDefault(mission.getResourceId(),
						mission.getResourceId()));
			}
			missionStageScheme.setStage(schemeDatum.getStage());
			missionStageScheme.setDynamic(schemeDatum.getDynamic());
			missionStageScheme.setMissions(targetMissionIds);

			missionStageSchemes.add(missionStageScheme);
		}

		return JsonUtil.toJsonString(missionStageSchemes);

	}
}
