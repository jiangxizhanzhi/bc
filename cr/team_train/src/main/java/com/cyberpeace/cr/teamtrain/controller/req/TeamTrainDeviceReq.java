package com.cyberpeace.cr.teamtrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 团队训练装置请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TeamTrainDeviceReq {
    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty(value = "团队房间id")
    @NotEmpty
    private String roomId;

    /**
     * 装置id
     *
     * @see String
     */
    @ApiModelProperty(value = "设备id")
    @NotEmpty
    private String deviceId;
}
