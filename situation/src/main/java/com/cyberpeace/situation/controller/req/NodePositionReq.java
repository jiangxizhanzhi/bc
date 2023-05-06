package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.PositionContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点坐标请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class NodePositionReq implements Serializable {

	/**
	 * 环境配置方案ID
	 */
	@ApiModelProperty("环境配置方案ID")
	private String envId;

	/**
	 * 节点坐标
	 */
	@ApiModelProperty("节点坐标")
	private Map<String, PositionContext> position;

}
