package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskRsp implements Serializable {

	/**
	 * 模板id
	 */
	@ApiModelProperty("测评靶场模板Id")
	private String templateId;

	/**
	 * 模板名称
	 */
	@ApiModelProperty("测评名称")
	private String templateName;

	/**
	 * 任务id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty("测评任务名称")
	private String taskName;

	/**
	 * 当地场景id
	 */
	@ApiModelProperty("场景管理器id")
	private String localSceneId;

	/**
	 * 中台场景id
	 */
	@ApiModelProperty("场景id")
	private String mpSceneId;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑id")
	private String topoId;

	/**
	 * 拓扑数据
	 */
	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
