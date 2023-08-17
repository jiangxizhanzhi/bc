package com.cyberpeace.cr.redvsblue.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.ChartInfo;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomModifyReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateListReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTimeManagementReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRoleRes;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTimeManagementRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.RbDeviceContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "红蓝对抗实例")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/")
public class RbRoomController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@Autowired
	private RbSceneService rbSceneService;

	@Autowired
	private RbDeviceService rbDeviceService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbTemplateMapper rbTemplateMapper;

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp create(@RequestBody @Validated RbRoomReq rbRoomReq) {
		// 校验
		rbRoomReq.validate();

		RbRoomContext rbRoomContext = BeanUtils.copy(rbRoomReq, RbRoomContext.class);
		rbRoomContext
				.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getPreSkill(), String.class));
		rbRoomContext
				.setTrainSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getTrainSkill(), String.class));
		rbRoomContext.setRoleConfig(
				JsonUtil.toJsonObjectArray(rbRoomReq.getRoleConfig(), RoomRole.class));

		RbRoom rbRoom = rbRoomService.create(rbRoomContext);

		RbTemplate rbTemplate = rbTemplateMapper.selectById(rbRoomReq.getTemplate());
		String rbTemplateName = businessTemplateMapper
				.selectById(rbTemplate.getBusinessTemplateId()).getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.CREATE, rbRoomReq.getName(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.RB_TRAIN_ROOM.getMsg(), rbRoomReq.getName()));

		return getRbRoom(rbRoom.getResourceId());
	}

	@ApiOperation("查询当前用户角色")
	@GetMapping("get_role")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE,MenuConstant.WEB_CR_EVALUATION}, logical = Logical.OR)
	public BaseRsp<BaseRole> getRbRoomRole(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		String userId = UserInfoContext.getUserContext().getUserId();
		crRoleService.checkPermission(resource_id, UserInfoContext.getUserContext().getUserId());

		RbRoomData rbRoomData = rbRoomService.getRbRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(rbRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		BaseRole baseRole = getSelfRoleUsers(crRoles, userId);


		return new BaseRsp(baseRole);
	}

	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbRoomRsp> getRbRoom(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		String userId = UserInfoContext.getUserContext().getUserId();
		crRoleService.checkPermission(resource_id, UserInfoContext.getUserContext().getUserId());

		RbRoomData rbRoomData = rbRoomService.getRbRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(rbRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
		rbRoomRsp.setRolePermission(getSelfRoleUsers(crRoles, userId));
		rbRoomRsp.setRolesData(getRbRoomRoleRes(crRoles));
		rbRoomRsp.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
		rbRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

		return new BaseRsp(rbRoomRsp);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<ListRsp<RbRoomRsp>> list(@Validated RbTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		String userId = UserInfoContext.getUserContext().getUserId();
		// 默认时间排序
		listQuery.setSort("-cr_rb_room.create_time");

		IPage<RbRoomData> data = rbRoomService.list(listQuery);

		// attack engage relation
		List<String> businessTemplateIds = data.getRecords().stream()
				.map(RbRoomData::getBusinessTemplateId).collect(Collectors.toList());
		List<String> attackRelatedIds = businessTemplateService
				.getAttackRelatedIds(businessTemplateIds);
		List<String> engageRelatedIds = businessTemplateService
				.getEngageRelatedIds(businessTemplateIds);

		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
				data.getRecords().stream().map(rbRoomData -> rbRoomData.getCopyedTopoId())
						.collect(Collectors.toList()));

		data.convert(rbRoomData -> {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(rbRoomData.getResourceId());

			List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

			RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
			rbRoomRsp.setNodesCount(
					topologyNodesStats.get(rbRoomData.getCopyedTopoId()).getNodeCounts());
			rbRoomRsp.setResourceOccupiedNums(
					topologyNodesStats.get(rbRoomData.getCopyedTopoId()).getResourceOccupiedNums());
			rbRoomRsp.setRolePermission(getSelfRoleUsers(crRoles, userId));
			rbRoomRsp.setPreSkill(
					JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
			rbRoomRsp.setTrainSkill(
					JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

			// set attack engage relation
			rbRoomRsp.setIsAttackRelated(
					attackRelatedIds.contains(rbRoomRsp.getBusinessTemplateId()));
			rbRoomRsp.setIsEngageRelated(
					engageRelatedIds.contains(rbRoomRsp.getBusinessTemplateId()));

			// 用户头像
			rbRoomRsp.setUserAvatar(UserInfoContext.getUserContext().getAvatarUrl());

			return rbRoomRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	private List<RbRoomRoleRes> getRbRoomRoleRes(List<CrRole> crRoles) {
		List<RbRoomRoleRes> rbRoomRoleResList = Lists.newArrayList();
		List<RbRoomRoleRes> redRoles = Lists.newArrayList();
		List<RbRoomRoleRes> blueRoles = Lists.newArrayList();

		for (CrRole crRole : crRoles) {
			RbRoomRoleRes rbRoomRoleRes = BeanUtils.copy(crRole, RbRoomRoleRes.class);
			rbRoomRoleRes.setUsersData(crRoleService.getUserDatas(crRole.getUserIdList()));
			rbRoomRoleRes.setUsers(crRole.getUserIdList());
			rbRoomRoleRes.setRoleGroup(crRole.getRoleGroup());

			if (crRole.isRedStudent()) {
				redRoles.add(rbRoomRoleRes);
			} else if (crRole.isBlueStudent()) {
				blueRoles.add(rbRoomRoleRes);
			} else {
				rbRoomRoleResList.add(rbRoomRoleRes);
			}
		}
		RbRoomRoleRes redRoleRes = new RbRoomRoleRes();
		redRoleRes.setName(CrRole.RbRoleGroup.RED.getDesc());
		redRoleRes.setResourceId("redResourceId");
		redRoleRes.setRoleGroup(CrRole.RbRoleGroup.RED.getCode());
		redRoleRes.setChildren(redRoles);
		rbRoomRoleResList.add(redRoleRes);

		RbRoomRoleRes blueRoleRes = new RbRoomRoleRes();
		blueRoleRes.setName(CrRole.RbRoleGroup.BLUE.getDesc());
		blueRoleRes.setResourceId("blueResourceId");
		blueRoleRes.setRoleGroup(CrRole.RbRoleGroup.BLUE.getCode());
		blueRoleRes.setChildren(blueRoles);
		rbRoomRoleResList.add(blueRoleRes);

		return rbRoomRoleResList;
	}

	/** 先获取实例列表中自己的角色，再获取所有角色(查看列表用到)
	 * @param crRoles
	 * @param userId
	 * @return
	 */
	private BaseRole getSelfRoleUsers(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();
		crRoles.forEach(crRole -> {
			if (crRole.containsUser(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());
				baseRole.setParentHash(crRole.getRoleGroup());
				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}

			RoleUsers roleUsers = new RoleUsers();
			roleUsers.setRoleName(crRole.getName());
			roleUsers.setRoleGroup(crRole.getRoleGroup());
			String userNamesByRoleUsers = crRoleService
					.getUserNamesByRoleUsers(crRole.getUserIdList());
			roleUsers.setUsers(JsonUtil.toJsonObjectArray(userNamesByRoleUsers, String.class));

			roleUsersList.add(roleUsers);

		});

		ChartInfo chartInfo = new ChartInfo();
		chartInfo.setInfoType(ChartInfo.ChartInfoType.NOT_RED_OR_BLUE_ONGOING.getCode());
		chartInfo.setRoleDataList(roleUsersList);

		baseRole.setChartInfo(chartInfo);

		return baseRole;
	}

	/**
	 * 查询团队训练学员用户列表
	 * @return
	 */
	@ApiOperation("获取控制面板信息，好友信息")
	@GetMapping("/dashboard_info")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<DashboardInfo> getDashboardInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole crRole = rbRoomService.checkRoomPermission(resource_id, userId);

		DashboardInfo data = rbRoomService.getDashboardInfo(crRole, resource_id, userId);

		return new BaseRsp(data);
	}

	/**
	 * 查询红蓝对抗学员用户列表
	 * @return
	 */
	@ApiOperation("获取群组成员列表")
	@GetMapping("/members_info")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getMembersInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "id", value = "角色id", required = true) String id) {
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole crRole = rbRoomService.checkRoomPermission(resource_id, userId);

		if (id.contains("_")) {
			String[] strings = id.split("_");
			id = strings[strings.length - 1];
		}
		List<DashboardUser> data = rbRoomService.getMembersInfo(id, resource_id);
		JSONObject json = new JSONObject();
		json.put("list", data);

		return new BaseRsp(json);
	}

	@ApiOperation("修改")
	@PutMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbRoomRsp> update(@RequestBody @Validated RbRoomModifyReq rbTrainRoomModifyReq) {
		// 校验
		rbTrainRoomModifyReq.validate();

		RbRoom rbRoom = rbRoomService.getById(rbTrainRoomModifyReq.getResourceId());

		if (rbRoom == null) {
			throw new ResourceNotFoundException(rbTrainRoomModifyReq.getResourceId());
		}
		RbRoomContext rbRoomContext = BeanUtils.copy(rbTrainRoomModifyReq, RbRoomContext.class);
		List<RoomRole> roomRoleList = JsonUtil
				.toJsonObjectArray(rbTrainRoomModifyReq.getRoleConfig(), RoomRole.class);
		rbRoomContext.setRoleConfig(roomRoleList);
		rbRoomContext.setPreSkill(
				JsonUtil.toJsonObjectArray(rbTrainRoomModifyReq.getPreSkill(), String.class));
		rbRoomContext.setTrainSkill(
				JsonUtil.toJsonObjectArray(rbTrainRoomModifyReq.getTrainSkill(), String.class));

		rbRoomService.updateRbRoom(rbRoomContext, rbRoom);

		rbRoom = rbRoomMapper.selectById(rbTrainRoomModifyReq.getResourceId());
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.UPDATE, rbRoom.getName(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.RB_TRAIN_ROOM.getMsg(), rbRoom.getName()));

		return getRbRoom(rbTrainRoomModifyReq.getResourceId());
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String> batchDelete(@RequestBody @Validated SceneOpsReq opsReq) {
		String roomId = opsReq.getRoomId();
		if (StringUtils.isBlank(roomId)) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		RbRoom rbRoom = rbRoomService.getById(roomId);
//		String rbTemplateName = rbRoomMapper.selectById(opsReq.getRoomId()).getTemplateName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.DELETE, rbRoom.getName(),
				LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.RB_TRAIN_ROOM.getMsg(), rbRoom.getName()));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbSceneService.stopScene(roomId);
				crRoleService.clearRoomRoles(roomId);
				rbRoomService.removeById(roomId);
			}
		});

		if (StringUtils.isNotBlank(rbRoom.getCopyedTopoId())) {
			try {
				remoteTopoService.deleteById(rbRoom.getCopyedTopoId());
			} catch (Exception e) {
				log.error("Red vs blue delete topo failed! room_id=[{}] topo_id=[{}]", roomId,
						rbRoom.getCopyedTopoId());
			}
		}

		return new BaseRsp<>();
	}

	/**
	 * 获取时间管理数据
	 * @return
	 */
	@ApiOperation("获取时间管理数据")
	@GetMapping("/get_time_management")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_EVALUATION}, logical = Logical.OR)
	public BaseRsp<RbTimeManagementRsp> getTimeManagement(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		RbTimeManagement rbTimeManagement = rbTimeManagementService.getByRoomId(resource_id);

		if (rbTimeManagement == null) {
			return new BaseRsp(null);
		}

		RbTimeManagementRsp rbTimeManagementRsp = BeanUtils.copy(rbTimeManagement,
				RbTimeManagementRsp.class);

		// 计算当前天文时间
		long time = rbTimeManagement.getAstronomicalTime().getTime() + System.currentTimeMillis()
				- rbTimeManagement.getCreateTime().getTime();
		Date currentAstronomicalTime = new Date(time);

		rbTimeManagementRsp.setCurrentAstronomicalTime(currentAstronomicalTime);
		rbTimeManagementRsp.setCreateUser(rbTimeManagement.getCreateUserId());
		rbTimeManagementRsp.setModifyTime(rbTimeManagement.getUpdateTime());
		rbTimeManagementRsp.setModifyUser(rbTimeManagement.getUpdateUserId());
		rbTimeManagementRsp.setRedVsBlue(rbTimeManagement.getRoomId());

		// 计算当前作战时间
		if (rbTimeManagement.isStopped() || rbTimeManagement.isPaused()) {
			rbTimeManagementRsp.setCurrentCombatTime(rbTimeManagement.getCombatTime());
		} else {
			time = rbTimeManagement.getCombatTime().getTime()
					+ (System.currentTimeMillis() - rbTimeManagement.getUpdateTime().getTime())
							* rbTimeManagement.getRate();
			Date currentCombatTime = new Date(time);
			rbTimeManagementRsp.setCurrentCombatTime(currentCombatTime);
		}

		return new BaseRsp(rbTimeManagementRsp);
	}

	/**
	 * 创建时间管理
	 * @return
	 */
	@ApiOperation("创建时间管理")
	@PostMapping("/create_time_management")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbTimeManagementRsp>
			addTimeManagement(@RequestBody @Validated RbTimeManagementReq rbTimeManagementReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		rbRoomService.checkDirectorPermission(rbTimeManagementReq.getRoomId(), userId);

		if (rbTimeManagementService.getByRoomId(rbTimeManagementReq.getRoomId()) != null) {
			log.error("Rbtrain has begun！roleId=[{}]", rbTimeManagementReq.getRoomId());
			throw new BusinessException(CrBusinessErrorCode.TRAIN_HAS_BEGUN);

		}

		Integer rate = rbTimeManagementReq.getRate();
		if (rate > RbTimeManagement.RateStatus.FIVE.getCode()
				|| rate < RbTimeManagement.RateStatus.ONCE.getCode()) {
			log.error("Rate Value Error！rate=[{}]", rate);
			throw new BusinessException(CrBusinessErrorCode.RATE_VALUE_RANGE_ERROR);
		}
		// 创建时间管理
		RbTimeManagement timeManagement = BeanUtils.copy(rbTimeManagementReq,
				RbTimeManagement.class);

		RbRoom rbRoom = rbRoomService.getById(rbTimeManagementReq.getRoomId());

		rbTimeManagementService.addTimeManagement(timeManagement, rbRoom,
				RbTimeManagement.TimeManagementStatus.START.getCode(), userId);

		RbTimeManagementRsp rbTimeManagementRsp = BeanUtils.copy(timeManagement,
				RbTimeManagementRsp.class);
		rbTimeManagementRsp.setRedVsBlue(timeManagement.getRoomId());

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.START, rbRoom.getName());

		return new BaseRsp(rbTimeManagementRsp);
	}

	@ApiOperation("获取设备占用情况")
	@GetMapping("/device")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbDeviceContext>> getDevices(
			@ApiParam(value = "room_id", name = "红蓝对抗id", required = true) String room_id,
			@ApiParam(value = "standard_device", name = "主机",
					required = false) String standard_device) {

		List<RbDeviceContext> ret = rbDeviceService.getByRoomIdAndOptionalDevice(room_id,
				standard_device);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("完成列表查询")
	@GetMapping("/finish/list")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	public BaseRsp<List<RbRoom>> getFinishList() {
		LambdaQueryWrapper<RbRoom> query = new LambdaQueryWrapper<>();
		query.eq(RbRoom::getTrainingStatus, RbRoom.TrainingStatusCode.ENDED.getCode());
		List<RbRoom> rbRooms = rbRoomMapper.selectList(query);
		return new BaseRsp<>(rbRooms);
	}
}
