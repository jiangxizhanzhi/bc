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
import java.util.stream.Collectors;

@Data
public class MissionReleaseStageInfo {

    @ApiModelProperty(value = "阶段")
    private String stage;

    @ApiModelProperty(value = "阶段任务列表")
    private List<MissionReleaseInfo> missionInfos;

    public MissionReleaseStageInfo() {
    }

    public MissionReleaseStageInfo(CrRoleMissionSchemeStage stage, List<Mission> allMissions, Map<String, List<CrTask>> taskMap, List<UserData> userInfoList) {
        List<Mission> missions = allMissions.stream().filter(a -> stage.getMissions() != null
                && stage.getMissions().contains(a.getResourceId())).collect(Collectors.toList());
        List<Mission> dynamicMissions = allMissions.stream().filter(a -> stage.getDynamic() != null
                && stage.getDynamic().contains(a.getResourceId())).collect(Collectors.toList());
        List<MissionReleaseInfo> missionReleaseInfos = new ArrayList<>();

        for (Mission mission : missions) {
            MissionReleaseInfo missionReleaseInfo = BeanUtils.copy(mission, MissionReleaseInfo.class);
            List<CrTask> crTasks = taskMap.getOrDefault(mission.getResourceId(), new ArrayList<>());
            Set<String> userIds = crTasks.stream().filter(CrTask::isSubmitted).map(CrTask::getUserId).collect(Collectors.toSet());
            missionReleaseInfo.setFinishUserList(userInfoList.stream().filter(a -> userIds.contains(a.getResourceId())).collect(Collectors.toList()));
            missionReleaseInfo.setNotFinishUserList(userInfoList.stream().filter(a -> !userIds.contains(a.getResourceId())).collect(Collectors.toList()));
            missionReleaseInfo.setIsRelease(crTasks.size() > 0);
            missionReleaseInfo.setIsDynamic(false);
            missionReleaseInfos.add(missionReleaseInfo);
        }
        for (Mission mission : dynamicMissions) {
            MissionReleaseInfo missionReleaseInfo = BeanUtils.copy(mission, MissionReleaseInfo.class);
            List<CrTask> crTasks = taskMap.getOrDefault(mission.getResourceId(), new ArrayList<>());
            Set<String> userIds = crTasks.stream().filter(CrTask::isSubmitted).map(CrTask::getUserId).collect(Collectors.toSet());
            missionReleaseInfo.setFinishUserList(userInfoList.stream().filter(a -> userIds.contains(a.getResourceId())).collect(Collectors.toList()));
            missionReleaseInfo.setNotFinishUserList(userInfoList.stream().filter(a -> !userIds.contains(a.getResourceId())).collect(Collectors.toList()));
            missionReleaseInfo.setIsRelease(crTasks.size() > 0);
            missionReleaseInfo.setIsDynamic(true);
            missionReleaseInfos.add(missionReleaseInfo);
        }
        this.setMissionInfos(missionReleaseInfos);
    }
}
