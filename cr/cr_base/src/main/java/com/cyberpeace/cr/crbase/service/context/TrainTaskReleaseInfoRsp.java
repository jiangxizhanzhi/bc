package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainTaskReleaseInfoRsp implements Serializable {

    private String roleName;

    private List<UserInfo> userInfoList;

    private List<MissionReleaseStageInfo>  missionStages;
}
