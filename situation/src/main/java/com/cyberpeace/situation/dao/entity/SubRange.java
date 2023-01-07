package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;
import java.util.Arrays;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.integratation.constant.BcStatus;
import com.cyberpeace.integratation.constant.BcType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_sub_range")
public class SubRange implements Serializable {

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("分靶场类型")
	private String subRangeType;

	@ApiModelProperty("分靶场位置")
	private String location;

	@ApiModelProperty("行政区划")
	private String region;

	@ApiModelProperty("分靶场地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("场站数量")
	private Integer stationCount;

	@ApiModelProperty("分靶场状态")
	private String status;

	public void setGroupId(String groupId) {
		this.subRangeId = groupId;
	}

	public void setName(String name) {
		this.subRangeName = name;
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

	public void setBcType(String bcType) {
		this.subRangeType = Arrays.stream(BcType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(bcType)).findFirst().orElse(BcType.MY).getName();;
	}

	public void setBcNumber(String bcNumber) {
		this.stationCount = Integer.parseInt(bcNumber);
	}

}
