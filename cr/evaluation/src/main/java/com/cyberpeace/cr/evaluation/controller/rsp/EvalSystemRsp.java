package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalSystemRsp {

	/**
	 * 评估系统id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 连接列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	/**
	 * 第一个索引
	 *
	 * @see List<IndexContext>
	 */
	@ApiModelProperty(value = "一级指标列表", required = true)
	@NotNull
	private List<IndexContext> firstIndexes;

	/**
	 * 第二个索引
	 *
	 * @see List<IndexContext>
	 */
	@ApiModelProperty(value = "二级指标列表", required = true)
	@NotNull
	private List<IndexContext> secondIndexes;

	/**
	 * 第三索引
	 *
	 * @see List<IndexContext>
	 */
	@ApiModelProperty(value = "三级指标列表", required = true)
	@NotNull
	private List<IndexContext> thirdIndexes;

}