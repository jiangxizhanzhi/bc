package com.cyberpeace.cr.teamtrain.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团队训练装置上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TeamTrainDeviceContext {
    /**
     * id
     *
     * @see String
     */
    @ApiModelProperty(value = "自增id")
    private String id;

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty(value = "团队id")
    private String roomId;

    /**
     * 装置id
     *
     * @see String
     */
    @ApiModelProperty(value = "主机id")
    private String deviceId;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty(value = "用户id")
    private String createUserId;

    /**
     * 创建用户
     *
     * @see String
     */
    @ApiModelProperty(value = "用户姓名")
    private String createUser;

    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
