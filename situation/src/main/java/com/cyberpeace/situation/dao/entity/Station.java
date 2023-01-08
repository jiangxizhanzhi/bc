package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;
import java.util.Arrays;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.integratation.constant.BcStatus;
import com.cyberpeace.integratation.constant.BcType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_station")
public class Station extends BaseTable implements Serializable {

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

	@ApiModelProperty("行政区划")
	private String region;

	@ApiModelProperty("场站地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("场站状态")
	private String status;

	public void setGroupId(String groupId) {
		this.stationId = groupId;
	}

	public void setName(String name) {
		this.stationName = name;
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
		this.stationType = Arrays.stream(BcType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(bcType)).findFirst().orElse(BcType.FCZ).getName();;
	}

	public void setParentId(String parentId) {
		this.subRangeId = parentId;
	}

}
