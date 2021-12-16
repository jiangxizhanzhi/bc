package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.ChartInfo;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomModifyReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateListReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRoleRes;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.cr.redvsblue.service.context.room.DashboardInfo;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "红蓝对抗实例")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/")
public class RbRoomController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;


	@Autowired
	private TransactionTemplate transactionTemplate;
	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp create(@RequestBody @Validated RbRoomReq rbRoomReq) {
		RbRoomContext rbRoomContext = BeanUtils.copy(rbRoomReq, RbRoomContext.class);
		rbRoomContext
				.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getPreSkill(), String.class));
		rbRoomContext
				.setTrainSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getTrainSkill(), String.class));
		rbRoomContext.setRoleConfig(
				JsonUtil.toJsonObjectArray(rbRoomReq.getRoleConfig(), RoomRole.class));
		RbRoom rbRoom = rbRoomService.create(rbRoomContext);

		return getRbRoom(rbRoom.getResourceId());
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<RbRoomRsp> getRbRoom(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		RbRoomData rbRoomData = rbRoomService.getRbRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(rbRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
		rbRoomRsp.setRolePermission(getSelfRoleUsers(crRoles, userId));

		rbRoomRsp.setRolesData(getRbRoomRoleRes(crRoles));
		// todo rbRoomRsp.setTrainingStatus();
		rbRoomRsp.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
		rbRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

		return new BaseRsp(rbRoomRsp);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<RbRoomRsp>> list(@Validated RbTemplateListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		String userId = UserInfoContext.getUserContext().getUserId();
		// 默认时间排序4
		listQuery.setSort("-cr_rb_room.create_time");

		IPage<RbRoomData> data = rbRoomService.list(listQuery);

		data.convert(rbRoomData -> {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(rbRoomData.getResourceId());

			List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

			RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
			rbRoomRsp.setRolePermission(getSelfRoleUsers(crRoles, userId));
			rbRoomRsp.setPreSkill(
					JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
			rbRoomRsp.setTrainSkill(
					JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

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
			rbRoomRoleRes.setUsersData(crRoleService.getUserDatas(crRole.getUserIds()));
			rbRoomRoleRes.setUsers(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));

			if (crRole.getRoleGroup().equals(CrRole.RbRoleGroupCode.RED.getCode())) {
				redRoles.add(rbRoomRoleRes);
			} else if (crRole.getRoleGroup().equals(CrRole.RbRoleGroupCode.BLUE.getCode())) {
				blueRoles.add(rbRoomRoleRes);
			} else {
				rbRoomRoleResList.add(rbRoomRoleRes);
			}
		}
		RbRoomRoleRes redRoleRes = new RbRoomRoleRes();
		redRoleRes.setName(CrRole.RbRoleGroupCode.RED.getDesc());
		redRoleRes.setRoleGroup(CrRole.RbRoleGroupCode.RED.getCode());
		redRoleRes.setChildren(redRoles);
		rbRoomRoleResList.add(redRoleRes);

		RbRoomRoleRes blueRoleRes = new RbRoomRoleRes();
		blueRoleRes.setName(CrRole.RbRoleGroupCode.BLUE.getDesc());
		blueRoleRes.setRoleGroup(CrRole.RbRoleGroupCode.BLUE.getCode());
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
			if (crRole.getUserIds().contains(userId)) {
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
					.getUserNamesByRoleUsers(crRole.getUserIds());
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
	public BaseRsp<DashboardInfo> getDashboardInfo(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		DashboardInfo data = rbRoomService.getDashboardInfo(resource_id);

		return new BaseRsp<>(data);
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<RbRoomRsp> update(@RequestBody @Validated RbRoomModifyReq rbTrainRoomModifyReq) {

		RbRoom rbRoom = rbRoomService.getById(rbTrainRoomModifyReq.getResourceId());

		if (rbRoom == null) {
			throw new ResourceNotFoundException(rbTrainRoomModifyReq.getResourceId());
		}
		RbRoomContext rbRoomContext = BeanUtils.copy(rbTrainRoomModifyReq, RbRoomContext.class);
		List<RoomRole> roomRoleList = JsonUtil
				.toJsonObjectArray(rbTrainRoomModifyReq.getRoleConfig(), RoomRole.class);
		rbRoomContext.setRoleConfig(roomRoleList);
		rbRoomService.updateRbRoom(rbRoomContext, rbRoom);

		return getRbRoom(rbTrainRoomModifyReq.getResourceId());
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				// todo teamSceneService.stopScene(deleteReq.getIds().get(0));
				rbRoomService.removeByIds(deleteReq.getIds());

			}
		});

		return new BaseRsp<>();
	}
}
