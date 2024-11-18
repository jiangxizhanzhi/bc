package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/11
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class NodeUpdateReq implements Serializable {

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * 更新
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty(value = "节点更新内容")
	private JSONObject update;
}
