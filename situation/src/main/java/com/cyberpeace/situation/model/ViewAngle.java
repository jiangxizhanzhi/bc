package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ViewAngle implements Serializable {

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("高程（米）")
	private Integer altitude;

	@ApiModelProperty("旋转值")
	private Float heading;

	@ApiModelProperty("视角")
	private Float  pitch;

	@ApiModelProperty("距离中心距离")
	private Float rang;

	public ViewAngle() {}

	public ViewAngle(String longitude, String latitude, Integer altitude, Float heading, Float pitch,
			Float rang) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.heading = heading;
		this.pitch = pitch;
		this.rang = rang;
	}

}
