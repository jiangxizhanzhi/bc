package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalTaskRsp implements Serializable {

	/**
	 * 模板id
	 *
	 * @see String
	 */
	@ApiModelProperty("测评靶场模板Id")
	private String templateId;

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	@ApiModelProperty("测评名称")
	private String templateName;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	@ApiModelProperty("测评任务名称")
	private String taskName;

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景管理器id")
	private String localSceneId;

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String mpSceneId;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑id")
	private String topoId;

	/**
	 * 拓扑数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
