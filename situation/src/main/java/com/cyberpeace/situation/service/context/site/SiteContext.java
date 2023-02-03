package com.cyberpeace.situation.service.context.site;

import java.io.Serializable;
import java.util.Arrays;

import com.cyberpeace.integratation.constant.BcStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SiteContext implements Serializable {

	@ApiModelProperty("地理位置")
	private String location;

	@ApiModelProperty("行政区划")
	private String region;

	@ApiModelProperty("具体地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("状态")
	private String status;

	public void setBcLocation(String bcLocation) {
		this.location = bcLocation;
	}

	public void setOrganizeAddress(String organizeAddress) {
		this.region = organizeAddress;
	}

	public void setBcAddress(String bcAddress) {
		this.address = bcAddress;
	}

	public void setBcLongitude(String bcLongitude) {
		this.longitude = bcLongitude;
	}

	public void setBcLatitude(String bcLatitude) {
		this.latitude = bcLatitude;
	}

	public void setBcStatus(String bcStatus) {
		this.status = Arrays.stream(BcStatus.class.getEnumConstants())
				.filter(t -> t.getCode().equals(bcStatus)).findFirst().orElse(BcStatus.WJC)
				.getName();
	}

	public String getId() {
		return null;
	}

}
