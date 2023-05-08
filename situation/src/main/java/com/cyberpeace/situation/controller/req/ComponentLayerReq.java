package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增组件图层请求
 *
 * @Author xuxz
 * @Date 30/12/2022 上午11:57
 */
@Data
public class ComponentLayerReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 组件id
	 */
	@NotBlank(groups = Create.class)
	@ApiModelProperty("组件id")
	private String componentId;

	/**
	 * 模板id
	 */
	@ApiModelProperty("模板id")
	private String templateId;

	/**
	 * 图层名称
	 */
	@NotBlank(groups = Create.class)
	@ApiModelProperty("图层名称")
	private String name;

	/**
	 * 图层序号
	 */
	@NotNull(groups = Create.class)
	@ApiModelProperty("图层序号")
	private Integer order;

	/**
	 * 组合图层
	 */
	@ApiModelProperty("组合图层")
	private List<ComponentLayerReq> combination;
}
