package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbRoomTeamScoreRsp implements Serializable {
    @ApiModelProperty("已完成分值")
    private Integer finishedScore;

    @ApiModelProperty("未完成分值")
    private Integer unfinishedScore;

    @ApiModelProperty("队伍角色得分占比")
    private List<RbRoomRoleScoreRsp> roleScoreList;
}
