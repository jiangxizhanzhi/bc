package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 业务组件设置请求
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
public class BusinessComponentsSettingReq implements Serializable {

	/**
	 * 资源ID
	 */
	@ApiModelProperty("资源ID")
	private String resourceId;

	/**
	 * 组件类型
	 */
	@ApiModelProperty("组件类型")
	private String type;

	/**
	 * 数据源id
	 */
	@ApiModelProperty("数据源id")
	private String datasourceId;

	/**
	 * 数据源配置
	 */
	@ApiModelProperty("数据源配置")
	private Object datasourceSetting;

	/**
	 * 组件id
	 */
	@ApiModelProperty("组件id")
	private String componentsId;

	/**
	 * 组件配置
	 */
	@ApiModelProperty("组件配置")
	private Object componentSetting;

	/**
	 * 图层顺序
	 */
	@ApiModelProperty("图层顺序")
	private Integer componentIndex;
}
