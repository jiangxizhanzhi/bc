package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点行为分页请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class NodeActionPageReq extends NodePageReq implements Serializable {

	/**
	 * 行为类型
	 */
	@ApiModelProperty(name = "action_type", value = "行为类型")
	private String actionType;

	/**
	 * 设置行为类型
	 *
	 * @param actionType 行为类型
	 */
	public void setAction_type(String actionType) {
		this.actionType = actionType;
	}

}
