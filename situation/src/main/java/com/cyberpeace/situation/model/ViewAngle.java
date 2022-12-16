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

	@ApiModelProperty("倾斜值")
	private Float pitch;

	@ApiModelProperty("偏移值")
	private Float yaw;

	@ApiModelProperty("旋转值")
	private Float roll;

	public ViewAngle() {}

	public ViewAngle(String longitude, String latitude, Integer altitude, Float pitch, Float yaw,
			Float roll) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

}
