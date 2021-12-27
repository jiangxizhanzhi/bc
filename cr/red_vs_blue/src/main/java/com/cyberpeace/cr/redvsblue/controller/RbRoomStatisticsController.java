package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;
import com.cyberpeace.cr.redvsblue.controller.rsp.TimeDataRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.mission.MissionsStatistic;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "红蓝对抗实例统计")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/statistics/")
public class RbRoomStatisticsController {

	@Autowired
	private RbRoomScoreService rbRoomScoreService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 查询红蓝实时战况页面 正中间计时器
	 * 获取实例运行时间和状态
	 * @return
	 */
	@ApiOperation("获取实例运行时间和状态")
	@GetMapping("/rb_time_data")
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
	 * 查询任务状态
	 * 导演-任务编排 左侧柱状图
	 * @return
	 */
	@ApiOperation("查询任务状态")
	@GetMapping("/missions_statistics")
	public BaseRsp<List<MissionsStatistic>> getMissionsStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		List<MissionsStatistic> missionsStatistics = Lists.newArrayList();
		List<String> allUserIds = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectRbStudentsRoleByRoomId(resource_id);
		for (CrRole crRole : crRoles) {
			List<String> userIds = JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class);

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

}
