package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoomDeviceOccupyRsp implements Serializable {
    private String resourceId;

    @ApiModelProperty("申请用户id")
    private String applyUserId;

    @ApiModelProperty("申请用户角色id")
    private String applyRoleId;

    @ApiModelProperty("申请用户名")
    private String ApplyUsername;

    @ApiModelProperty("审批用户id")
    private String allowUserId;

    @ApiModelProperty("审批时间")
    private Date allowTime;

    @ApiModelProperty("训练实例id")
    private String roomId;
}