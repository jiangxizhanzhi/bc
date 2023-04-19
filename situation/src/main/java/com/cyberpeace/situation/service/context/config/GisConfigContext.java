package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import com.cyberpeace.situation.constant.MapSource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GisConfigContext implements Serializable {

	@ApiModelProperty(value = "配置文件ID")
	private String configId;

	@ApiModelProperty(value = "地图数据源")
	private Integer mapSource = MapSource.GGIM.getCode();

}
