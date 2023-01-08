package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;
import java.util.Arrays;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.integratation.constant.BcStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_center")
public class Center extends BaseTable implements Serializable {

	@ApiModelProperty("LG中心ID")
	private String centerId;

	@ApiModelProperty("LG中心名称")
	private String centerName;

	@ApiModelProperty("LG中心位置")
	private String location;

	@ApiModelProperty("行政区划")
	private String region;

	@ApiModelProperty("LG中心地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("状态")
	private String status;

	public void setGroupId(String groupId) {
		this.centerId = groupId;
	}

	public void setName(String name) {
		this.centerName = name;
	}

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
				.getName();;
	}

}
