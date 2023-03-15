package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import com.cyberpeace.situation.constant.MapSource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GisConfigContext implements Serializable {

	@ApiModelProperty(value = "配置文件ID")
	private String configId;

	@ApiModelProperty(value = "地图数据源 1-谷歌 2-百度 3-高德")
	private Integer mapSource = MapSource.GG.getCode();

}
