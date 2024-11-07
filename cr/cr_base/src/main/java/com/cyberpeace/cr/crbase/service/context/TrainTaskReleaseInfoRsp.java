package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseInfo;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 训练任务释放信息响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainTaskReleaseInfoRsp implements Serializable {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色名称")
    private String roleId;

    /**
     * 用户信息列表
     */
    @ApiModelProperty(value = "用户列表")
    private List<UserData> userInfoList;

    /**
     * 任务阶段
     */
    @ApiModelProperty(value = "任务列表")
    private List<MissionReleaseStageInfo>  missionStages;

    /**
     * 释放计数
     */
    @ApiModelProperty(value = "下发的任务数")
    private Integer releaseCount;

    /**
     * 都计数
     */
    @ApiModelProperty(value = "总任务数")
    private Integer allCount;

    public void initCount() {
        List<MissionReleaseInfo> missionList = missionStages.stream().map(a -> a.getMissionInfos()).flatMap(Collection::stream).collect(Collectors.toList());
        this.allCount = missionList.size();
        this.releaseCount = (int)missionList.stream().filter(a->a.getIsRelease()).count();

    }
}
