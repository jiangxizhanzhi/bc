package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubRangeContext implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("分靶场类型")
	private String subRangeType;

	@ApiModelProperty("分靶场位置")
	private String location;

	@ApiModelProperty("分靶场地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("场站数量")
	private Integer stationCount;

	public SubRangeContext() {}

	public SubRangeContext(String subRangeId, String subRangeName) {
		this.subRangeId = subRangeId;
		this.subRangeName = subRangeName;
	}

}
