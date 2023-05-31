package com.cyberpeace.cr.teamtrain.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TeamTrainDeviceContext {
    @ApiModelProperty(value = "自增id")
    private String id;

    @ApiModelProperty(value = "团队id")
    private String roomId;

    @ApiModelProperty(value = "主机id")
    private String deviceId;

    @ApiModelProperty(value = "用户id")
    private String createUserId;

    @ApiModelProperty(value = "用户姓名")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
