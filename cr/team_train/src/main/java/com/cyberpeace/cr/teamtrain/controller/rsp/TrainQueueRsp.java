package com.cyberpeace.cr.teamtrain.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrainQueueRsp {
    @ApiModelProperty("记录唯一标识")
    private String resourceId;


    @ApiModelProperty("团队训练roomId")
    private String roomId;

    @ApiModelProperty("团队训练room 名称")
    private String roomName;

    @ApiModelProperty("占用或者需要等待的实体设备资源id")
    private List<String> entityDeviceIds;

    @ApiModelProperty("申请时间, 格式YYYY-MM-DD HH:MM:SS")
    private String applyTime;

    @ApiModelProperty("状态 0-排队中 1-启用中")
    private Integer status;


}
