package com.cyberpeace.cr.teamtrain.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团队训练装置上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamTrainDeviceContext {
    /**
     * id
     */
    @ApiModelProperty(value = "自增id")
    private String id;

    /**
     * 房间id
     */
    @ApiModelProperty(value = "团队id")
    private String roomId;

    /**
     * 装置id
     */
    @ApiModelProperty(value = "主机id")
    private String deviceId;

    /**
     * 创造用户id
     */
    @ApiModelProperty(value = "用户id")
    private String createUserId;

    /**
     * 创建用户
     */
    @ApiModelProperty(value = "用户姓名")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
