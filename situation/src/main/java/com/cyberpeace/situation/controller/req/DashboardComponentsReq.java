package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午6:06
 */
@Data
public class DashboardComponentsReq implements Serializable {

	@ApiModelProperty("组件类型")
	private String resourceId;

	@ApiModelProperty("组件类型")
	private String type;

	@ApiModelProperty("数据源id")
	private String datasourceId;

	@ApiModelProperty("数据源配置")
	private Object datasourceSetting;

	@ApiModelProperty("组件id")
	private String componentsId;

	@ApiModelProperty("组件配置")
	private Object componentSetting;

	@ApiModelProperty("图层顺序")
	private Integer componentIndex;
}
