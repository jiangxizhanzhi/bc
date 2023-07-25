package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备在线状态
 *
 * @author SN
 * @date 2023/07/25
 */
@Data
public class OnlineStatusRsp implements Serializable {

	@ApiModelProperty(value = "在线")
	private List<String> online = Lists.newArrayList();

	@ApiModelProperty(value = "离线")
	private List<String> offline = Lists.newArrayList();
}
