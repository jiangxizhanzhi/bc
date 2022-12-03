package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

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

	@ApiModelProperty("场站地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("场站状态")
	private String status;

}
