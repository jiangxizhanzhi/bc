package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoomDeviceApplyListRsp implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("训练实例id")
    private String roomId;

    @ApiModelProperty("训练实例名称")
    private String roomName;

    @ApiModelProperty("申请时间")
    private Date applyTime;

    @ApiModelProperty("申请用户名称")
    private String applyUsername;

    @ApiModelProperty("申请用户id")
    private String applyUserId;

    @ApiModelProperty("审批状态")
    private Integer status;
}
