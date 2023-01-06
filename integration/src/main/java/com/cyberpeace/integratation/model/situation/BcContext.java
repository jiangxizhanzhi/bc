package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BcContext implements Serializable {

	@ApiModelProperty("组织机构代号")
	private String groupId;

	@ApiModelProperty("父机构代号")
	private String parentId;

	@ApiModelProperty("机构名称")
	private String name;

	@ApiModelProperty("省级行政")
	private String organizeAddress;

	@ApiModelProperty("市级行政")
	private String bcLocation;

	@ApiModelProperty("靶场地址")
	private String bcAddress;

	@ApiModelProperty("靶场经度")
	private String bcLongitude;

	@ApiModelProperty("靶场纬度")
	private String bcLatitude;

	@ApiModelProperty("靶场状态")
	private String bcStatus;

	@ApiModelProperty("靶场类型")
	private String bcType;

	@ApiModelProperty("机构类型")
	private String type;

}
