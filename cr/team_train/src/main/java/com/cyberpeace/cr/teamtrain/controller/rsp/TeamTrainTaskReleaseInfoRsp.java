package com.cyberpeace.cr.teamtrain.controller.rsp;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeamTrainTaskReleaseInfoRsp implements Serializable {

    private String roleName;

    private List<UserInfo> userInfoList;

    private List<MissionReleaseStageInfo>  missionStages;
}
