package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RbSituationMissionStatusRsp implements Serializable {
    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("任务标题")
    private String missionTitle;

    @ApiModelProperty("任务状态")
    private Integer status;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("是否完成")
    public Boolean getIsFinished() {
        return status != null && CrTask.TaskStatus.FINISHED_STATUS_LIST.contains(status) ? Boolean.TRUE : Boolean.FALSE;
    }
}
