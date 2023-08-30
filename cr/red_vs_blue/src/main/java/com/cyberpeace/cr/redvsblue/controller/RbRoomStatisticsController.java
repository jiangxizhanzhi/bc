package com.cyberpeace.cr.redvsblue.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import com.cyberpeace.cr.redvsblue.controller.rsp.*;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.XYData;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cyberpeace.common.util.EnumUtils.getListCode;

@Api(tags = "红蓝对抗实例统计")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/statistics/")
public class RbRoomStatisticsController {

	@Autowired
	private RbRoomScoreService rbRoomScoreService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private RemoteUserService remoteUserService;
	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	/**
	 * 查询红蓝实时战况页面 正中间计时器
	 * 获取实例运行时间和状态
	 * @return
	 */
	@ApiOperation("获取实例运行时间和状态")
	@GetMapping("/rb_time_data")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TimeDataRsp> getRbTimeData(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		TimeData data = rbRoomScoreService.getRbTimeData(resource_id);

		TimeDataRsp timeDataRsp = new TimeDataRsp();
		timeDataRsp.setStartTime((int) (data.getStartTime().getTime() / 1000));
		timeDataRsp.setTimeStamp((int) (System.currentTimeMillis() / 1000));
		timeDataRsp.setRunning(data.getRunning());
		timeDataRsp.setStatus(data.getStatus());

		return new BaseRsp(timeDataRsp);
	}

	/**
	 * 红蓝实时战况页面 左右两侧 任务分类 柱状图
	 *     获取实例下的角色任务分类分布数据
	 * @return
	 */
	@ApiOperation("获取实例下的角色任务分类分布数据")
	@GetMapping("/team_mission_category")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<XYData> getRbMissionCategory(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resource_id, userId, role_type);

		XYData data = rbRoomScoreService.getRbMissionCategory(resource_id, role_type);

		return new BaseRsp(data);
	}

	/**
	 * 指定队伍（红/蓝）排行榜 得分排名 任务进展排名
	 * @return
	 */
	@ApiOperation("排行榜大屏展示")
	@GetMapping("/team_leader_board")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbLeaderBoard(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resource_id, userId, role_type);

		JSONObject data = rbRoomScoreService.getRbLeaderBoard(resource_id, role_type);

		return new BaseRsp(data);
	}

	/**
	 * 红蓝实时战况页面 左右两侧 任务分类 柱状图
	 *     获取实例下的角色任务分类分布数据
	 * @return
	 */
	@ApiOperation("获取实例下的角色任务分类分布数据")
	@GetMapping("/team_members_ranking")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMembersRanking(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resource_id, userId, role_type);

		JSONObject data = rbRoomScoreService.getRbMembersRanking(resource_id, role_type);

		return new BaseRsp(data);
	}

	/**
	 * 红蓝实时战况页面 左右两侧 任务分类 柱状图
	 *     获取实例下的角色任务分类分布数据
	 * @return
	 */
	@ApiOperation("获取实例下的任务评估数据统计")
	@GetMapping("/team_mission_evaluation")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMissionEvaluation(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resource_id, userId, role_type);

		JSONObject data = rbRoomScoreService.getRbMissionEvaluation(resource_id, role_type);

		return new BaseRsp(data);
	}

	/**
	 * 查询任务状态
	 * 导演-任务编排 左侧柱状图
	 * @return
	 */
	@ApiOperation("练习情况概览")
	@GetMapping("/missions_statistics")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionsStatistic>> getMissionsStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		List<MissionsStatistic> missionsStatistics = Lists.newArrayList();
		List<String> allUserIds = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectRbStudentsRoleByRoomId(resource_id);
		for (CrRole crRole : crRoles) {
			List<String> userIds = crRole.getUserIdList();

			allUserIds.addAll(userIds);

			for (String userId : userIds) {
				TaskCounter taskCounter = crRoleService.getTaskCounter(resource_id, userId);
				MissionsStatistic missionsStatistic = new MissionsStatistic(crRole.getRoleGroup(),
						userId, taskCounter);
				missionsStatistics.add(missionsStatistic);
			}
		}

		Map<String, String> idNameMap = remoteUserService.getIdNameMap(allUserIds);
		missionsStatistics.forEach(missionsStatistic -> {
			missionsStatistic.setUserName(idNameMap.get(missionsStatistic.getUserResourceId()));
		});

		return new BaseRsp(missionsStatistics);
	}

	/**
	 *  红方/蓝方成绩页面 右侧中折线图 任务进展
	 *     红蓝实时战况页面 左右两侧 任务进展
	 *     队内任务下发和完成任务数进展
	 * @return
	 */
	@ApiOperation("成果大屏展示")
	@GetMapping("/team_mission_progress")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMissionProgress(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resource_id, userId, role_type);

		JSONObject data = rbRoomScoreService.getRbMissionProgress(resource_id, role_type);

		return new BaseRsp(data);
	}

	/**
	 *  红方/蓝方成绩页面 右侧下折线图 得分趋势
	 *     队伍得分趋势统计
	 * @return
	 */
	@ApiOperation("实况大屏展示")
	@GetMapping("/team_score_progress")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbScoreProgress(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		if (CrRole.RbRoleGroup.RED_BLUE.equals(role_type)) {
			return new BaseRsp(getRbScoreProgress_6(resource_id, role_type, userId));
		}

		permissionCheckTeacherOrGroup(resource_id, userId, role_type);

		JSONObject data = rbRoomScoreService.getRbScoreProgress(resource_id, role_type);

		return new BaseRsp(data);
	}

	/**
	 *  红蓝实时战况页面 正下方 红蓝得分
	 * @return
	 */
	private JSONObject getRbScoreProgress_6(String resource_id, Integer role_type, String userId) {

		rbRoomService.checkRoomPermission(resource_id, userId);

		return rbRoomScoreService.getAllRbScoreProgress(resource_id, role_type);
	}

	private void permissionCheckTeacherOrGroup(String resource_id, String userId, Integer role_type) {
		CrRole crRole = rbRoomService.checkRoomPermission(resource_id, userId);

		if (crRole.isRvbStudent() && !crRole.getRoleGroup().equals(role_type)) {
			log.error("user does not have RbRoom Permission！roomId=[{}], userId=[{}]", resource_id, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	@ApiOperation("3D态势-队伍得分")
	@GetMapping("/team_score")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbRoomTeamScoreRsp> teamScore(
			@ApiParam(name = "room_id", value = "训练id", required = true) String roomId,
			@ApiParam(name = "type", value = "队伍类型 4-红;5-蓝", required = true) String type
	) {
		return new BaseRsp<>();
	}

	@ApiOperation("3D态势-任务状态")
	@GetMapping("/team_task_status")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbRoomTeamMissionRsp>> teamTaskStatus(
			@ApiParam(name = "room_id", value = "训练id", required = true) String roomId,
			@ApiParam(name = "type", value = "队伍类型 4-红;5-蓝", required = true) String type
	) {
		return new BaseRsp<>();
	}

	@ApiOperation("3D态势-攻击与防守事件")
	@GetMapping("/team_defense_event")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbRoomTeamDefenseEventRsp>> teamDefenseEvent(
			@ApiParam(name = "room_id", value = "训练id", required = true) String roomId,
			@ApiParam(name = "type", value = "队伍类型 4-红;5-蓝", required = true) String type
	) {
		return new BaseRsp<>();
	}

	@ApiOperation("3D态势-资产状态")
	@GetMapping("/team_asset_status")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RbRoomTeamAssetStatusRsp> teamAssetStatus(
			@ApiParam(name = "room_id", value = "训练id", required = true) String roomId,
			@ApiParam(name = "type", value = "队伍类型 4-红;5-蓝", required = true) String type
	) {
		return new BaseRsp<>();
	}

	@ApiOperation("3D态势-队伍动态")
	@GetMapping("/team_action")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbRoomTeamActionRsp>> teamAction(
			@ApiParam(name = "room_id", value = "训练id", required = true) String roomId,
			@ApiParam(name = "type", value = "队伍类型 4-红;5-蓝", required = true) String type
	) {
		return new BaseRsp<>();
	}

	@ApiOperation("3D态势-平台动态")
	@GetMapping("/team_platform_action")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbRoomTeamActionRsp>> platformAction(
			@ApiParam(name = "room_id", value = "训练id", required = true) String roomId,
			@ApiParam(name = "type", value = "队伍类型 4-红;5-蓝", required = true) String type
	) {
		return new BaseRsp<>();
	}


	@ApiOperation("攻击方式统计")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	@GetMapping("/event/count")
	public BaseRsp<List<EvalEventCountBar>> getEventCount(
			@ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
			@RequestParam("role") @NotEmpty Integer type) {
		List<EvalEventCountBar> eventCountBarList = new ArrayList<>();
		List<EvalEventCountBar> taskByRoomIdAndUsers = crTaskLogMapper.getTaskByRoomIdAndType(roomId, CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
			Map<Integer, EvalEventCountBar> barMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventCountBar::getType, s -> s);
			List<Integer> listCode = getListCode(ActionTypeZHL.class);
			if (type.equals(CrRole.RbRoleGroup.RED.getCode())) {
				listCode = listCode.stream().filter(s -> s <= ActionTypeZHL.ZHL_ATTACK6.getCode()).collect(Collectors.toList());
			} else if (type.equals(CrRole.RbRoleGroup.BLUE.getCode())) {
				listCode = listCode.stream().filter(s -> s > ActionTypeZHL.ZHL_ATTACK6.getCode()).collect(Collectors.toList());
			} else {
				return new BaseRsp<>();
			}
			for (Integer tmp : listCode) {
				EvalEventCountBar rsp = new EvalEventCountBar();
				rsp.setCount(0);
				if (barMap.containsKey(tmp)) {
					EvalEventCountBar evalEventCountBar = barMap.get(tmp);
					rsp.setCount(evalEventCountBar.getCount());
				}
				String eventTypeStr = EnumUtils.getEnumDescByCode(tmp, ActionTypeZHL.class);
				rsp.setType(tmp);
				rsp.setEventName(eventTypeStr);
				eventCountBarList.add(rsp);
			}
			return new BaseRsp<>(eventCountBarList);
		}
}
