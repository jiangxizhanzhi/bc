package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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

}
