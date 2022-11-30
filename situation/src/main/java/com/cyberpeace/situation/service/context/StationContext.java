package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StationContext implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("场站ID")
	private String stationId;

	@ApiModelProperty("场站名称")
	private String stationName;

	@ApiModelProperty("场站类型")
	private String stationType;

	@ApiModelProperty("场站位置")
	private String location;

	@ApiModelProperty("场站地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("场站状态")
	private Integer status;

	public StationContext() {}

	public StationContext(String stationId, String stationName) {
		this.stationId = stationId;
		this.stationName = stationName;
	}

}
