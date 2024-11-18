package com.cyberpeace.cr.teamtrain.service.context;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队通用设备上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see TeamCommonDevice
 * @since 1.0.0
 */
@Data
public class TeamCommonDeviceContext extends TeamCommonDevice {
    /**
     * 占用标志
     *
     * @see Integer
     */
    @ApiModelProperty("是否占用")
    private Integer occupyFlag = 0;
    /**
     * 设备信息
     *
     * @see JSONObject
     */
    @ApiModelProperty("设备信息")
    private JSONObject deviceInfo;
    /**
     * collect 用户
     *
     * @see String
     */
    @ApiModelProperty("设备占用人")
    private String collectUser;
}
