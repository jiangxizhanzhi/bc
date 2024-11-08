package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 任务发布阶段信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MissionReleaseStageInfo {

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段")
	private String stage;

	/**
	 * 任务信息
	 */
	@ApiModelProperty(value = "阶段任务列表")
	private List<MissionReleaseInfo> missionInfos;

	/**
	 * 全部发布
	 */
	@ApiModelProperty(value = "是否全部下发")
	private Boolean isAllRelease = true;

	public MissionReleaseStageInfo() {}

	public MissionReleaseStageInfo(CrRoleMissionSchemeStage stage, List<Mission> allMissions,
			Map<String, List<CrTask>> taskMap, List<UserData> userInfoList) {
		Map<String, Mission> missionMaps = allMissions.stream().filter(
				a -> stage.getMissions() != null && stage.getMissions().contains(a.getResourceId()))
				.collect(Collectors.toMap(Mission::getResourceId, Function.identity()));
		List<String> dynamicMissions = allMissions.stream().map(Mission::getResourceId).filter(
				resourceId -> stage.getDynamic() != null && stage.getDynamic().contains(resourceId))
				.collect(Collectors.toList());
		List<MissionReleaseInfo> missionReleaseInfos = new ArrayList<>();

		if (stage.getMissions() != null) {
			for (String missionId : stage.getMissions()) {
				Mission mission = missionMaps.get(missionId);
				if (mission != null) {
					MissionReleaseInfo missionReleaseInfo = BeanUtils.copy(mission,
							MissionReleaseInfo.class);
					List<CrTask> crTasks = taskMap.getOrDefault(mission.getResourceId(),
							new ArrayList<>());
					Set<String> userIds = crTasks.stream().filter(CrTask::isSubmitted)
							.map(CrTask::getUserId).collect(Collectors.toSet());
					// 已下发 才有是否完成用户列表
					missionReleaseInfo.setFinishUserList(userInfoList.stream()
							.filter(a -> crTasks.size() > 0 && userIds.contains(a.getResourceId()))
							.collect(Collectors.toList()));
					missionReleaseInfo.setNotFinishUserList(userInfoList.stream()
							.filter(a -> crTasks.size() > 0 && !userIds.contains(a.getResourceId()))
							.collect(Collectors.toList()));
					missionReleaseInfo.setIsRelease(crTasks.size() > 0);
					//前端需要一个字段判断是否全部下发 如果有一个没有下发 就是false
					if (crTasks.size()==0) {
						this.isAllRelease = false;
					}
					missionReleaseInfo
							.setIsDynamic(dynamicMissions.contains(mission.getResourceId()));
					missionReleaseInfos.add(missionReleaseInfo);
				}
			}
		}
		this.stage = stage.getStage();
		this.setMissionInfos(missionReleaseInfos);
	}
}
