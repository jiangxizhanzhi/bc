package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import com.cyberpeace.situation.constant.gis.MapSource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GisConfigContext implements Serializable {

	@ApiModelProperty(value = "配置文件ID")
	private String configId;

	@ApiModelProperty(value = "地图数据源")
	private String mapSource = MapSource.GGIM.getName();

	public void setMapSource(Integer mapSource) {
		this.mapSource = MapSource.getMapSourceByCode(mapSource).getName();
	}

	public void setMapSource(String mapSource) {
		this.mapSource = mapSource;
	}

}
