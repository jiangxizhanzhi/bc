package com.cyberpeace.cr.redvsblue.service.context;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.entity.RbCommonDevice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗常用装置上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbCommonDeviceContext extends RbCommonDevice {
	/**
	 * 是否占用:1=已占用；0=未占用
	 */
	@ApiModelProperty("是否占用:1=已占用；0=未占用")
    private Integer occupyFlag = 0;
	/**
	 * 设备信息
	 */
	@ApiModelProperty("设备信息")
    private JSONObject deviceInfo;
	/**
	 * 占用人姓名
	 */
	@ApiModelProperty(value = "占用人姓名")
    private String collectUser;
}
