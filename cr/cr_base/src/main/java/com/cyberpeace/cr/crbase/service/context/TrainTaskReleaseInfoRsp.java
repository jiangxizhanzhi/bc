package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainTaskReleaseInfoRsp implements Serializable {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "用户列表")
    private List<UserData> userInfoList;

    @ApiModelProperty(value = "用户列表")
    private List<MissionReleaseStageInfo>  missionStages;
}
