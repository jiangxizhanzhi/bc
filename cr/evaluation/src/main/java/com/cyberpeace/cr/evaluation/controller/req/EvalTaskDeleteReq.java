package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskDeleteReq {

	/**
	 * 任务id列表
	 */
	@ApiModelProperty(value = "测评任务Ids", required = true)
	@NotNull
	@NotEmpty
	private List<String> taskIds;

}