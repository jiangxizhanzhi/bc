package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.dao.entity.ComponentLayer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组件图层返回
 *
 * @Author xuxz
 * @Date 30/12/2022 下午1:52
 */
@Data
public class ComponentLayerRes implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 组件id
	 */
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
	@ApiModelProperty("图层名称")
	private String name;

	/**
	 * 图层序号
	 */
	@ApiModelProperty("图层序号")
	private Integer order;

	/**
	 * 组合图层
	 */
	@ApiModelProperty("组合图层")
	private List<ComponentLayer> componentLayerList;
}
