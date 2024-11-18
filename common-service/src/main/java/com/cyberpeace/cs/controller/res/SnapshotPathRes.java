package com.cyberpeace.cs.controller.res;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 快照路径结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class SnapshotPathRes implements Serializable {

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("快照数量")
	private Integer count;

	/**
	 * 路径
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("快照路径 树形结构")
	private JSONObject path;

}
