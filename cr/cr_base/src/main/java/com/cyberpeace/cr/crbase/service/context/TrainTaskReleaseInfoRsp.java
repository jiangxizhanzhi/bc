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
 * 训练任务下发信息响应
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TrainTaskReleaseInfoRsp implements Serializable {

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 岗位id
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
     * 下发计数
     */
    @ApiModelProperty(value = "下发的任务数")
    private Integer releaseCount;

    /**
     * 全部计数
     */
    @ApiModelProperty(value = "总任务数")
    private Integer allCount;

    public void initCount() {
        List<MissionReleaseInfo> missionList = missionStages.stream().map(a -> a.getMissionInfos()).flatMap(Collection::stream).collect(Collectors.toList());
        this.allCount = missionList.size();
        this.releaseCount = (int)missionList.stream().filter(a->a.getIsRelease()).count();

    }
}
