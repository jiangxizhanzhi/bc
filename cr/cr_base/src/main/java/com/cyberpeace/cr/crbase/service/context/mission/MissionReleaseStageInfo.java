package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MissionReleaseStageInfo {


    private String stage;

    private List<MissionReleaseInfo> missionInfos;

    public MissionReleaseStageInfo() {
    }

    public MissionReleaseStageInfo(CrRoleMissionSchemeStage stage, List<Mission> allMissions, Map<String, List<CrTask>> taskMap, List<UserInfo> userInfoList) {
        List<Mission> missions = allMissions.stream().filter(a -> stage.getMissions() != null
                && stage.getMissions().contains(a.getResourceId())).collect(Collectors.toList());
        List<MissionReleaseInfo> missionReleaseInfos = new ArrayList<>();

        for (Mission mission : missions) {
            MissionReleaseInfo missionReleaseInfo = BeanUtils.copy(mission, MissionReleaseInfo.class);
            List<CrTask> crTasks = taskMap.getOrDefault(mission.getResourceId(), new ArrayList<>());
            Set<String> userIds = crTasks.stream().filter(CrTask::isSubmitted).map(CrTask::getUserId).collect(Collectors.toSet());
            missionReleaseInfo.setFinishUserList(userInfoList.stream().filter(a -> userIds.contains(a.getUserId())).collect(Collectors.toList()));
            missionReleaseInfo.setNotFinishUserList(userInfoList.stream().filter(a -> !userIds.contains(a.getUserId())).collect(Collectors.toList()));
            missionReleaseInfo.setIsRelease(crTasks.size() > 0);
            missionReleaseInfos.add(missionReleaseInfo);
        }
        this.setMissionInfos(missionReleaseInfos);
    }
}
