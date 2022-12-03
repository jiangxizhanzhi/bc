package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

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

	@ApiModelProperty("LG中心地址")
	private String address;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("状态")
	private String status;

}
