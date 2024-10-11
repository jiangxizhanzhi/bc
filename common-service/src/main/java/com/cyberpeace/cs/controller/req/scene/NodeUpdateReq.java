package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点更新请求体
 *
 * @author SN
 * @date 2023/05/11
 */
@Data
public class NodeUpdateReq implements Serializable {

	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	@ApiModelProperty(value = "节点更新内容")
	private JSONObject update;
}
