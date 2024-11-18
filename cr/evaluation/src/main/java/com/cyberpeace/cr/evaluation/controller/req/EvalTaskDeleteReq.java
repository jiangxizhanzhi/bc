package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalTaskDeleteReq {

	/**
	 * 任务id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "测评任务Ids", required = true)
	@NotNull
	@NotEmpty
	private List<String> taskIds;

}