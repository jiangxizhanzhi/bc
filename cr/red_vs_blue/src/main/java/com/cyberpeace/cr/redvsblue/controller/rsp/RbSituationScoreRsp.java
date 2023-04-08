package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbSituationScoreRsp implements Serializable {
    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("未得分数")
    private Integer residualScore;

    @ApiModelProperty("队伍角色得分占比信息")
    private List<RoleScoreInfo> roleScoreInfoList;

    @Data
    public static class RoleScoreInfo {
        @ApiModelProperty("角色Id")
        private String roleId;

        @ApiModelProperty("角色名")
        private String roleName;

        @ApiModelProperty("角色得分")
        private Integer score;

        @ApiModelProperty("角色得分占比")
        private Double percentage;
    }
}
