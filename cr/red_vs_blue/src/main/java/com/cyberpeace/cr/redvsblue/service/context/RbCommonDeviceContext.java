package com.cyberpeace.cr.redvsblue.service.context;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB 通用设备上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbCommonDeviceContext extends RbCommonDevice {
    /**
     * 占领标志
     */
    @ApiModelProperty("是否占用:1=已占用；0=未占用")
    private Integer occupyFlag = 0;
    /**
     * 设备信息
     */
    @ApiModelProperty("设备信息")
    private JSONObject deviceInfo;
    /**
     * collect 用户
     */
    @ApiModelProperty(value = "占用人姓名")
    private String collectUser;
}
