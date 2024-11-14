package com.cyberpeace.cr.teamtrain.service.context;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队常用装置上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamCommonDeviceContext extends TeamCommonDevice {
	/**
	 * 是否占用
	 */
	@ApiModelProperty("是否占用")
    private Integer occupyFlag = 0;
	/**
	 * 设备信息
	 */
	@ApiModelProperty("设备信息")
    private JSONObject deviceInfo;
	/**
	 * 设备占用人
	 */
	@ApiModelProperty("设备占用人")
    private String collectUser;
}
