package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 访问行为
 */
@Data
public class Behavior extends BehaviorSimple implements Serializable {

	@ApiModelProperty(value = "行为名称")
	private String name;

	@ApiModelProperty(value = "标签")
	private List<String> tag = Lists.newArrayList();

	@ApiModelProperty(value = "设备类型")
	private Integer accessOs;

	@ApiModelProperty(value = "网络模型")
	private Integer networkType;

	@ApiModelProperty(value = "行为步骤")
	private Integer stepCount;

	@ApiModelProperty(value = "行为耗时（min）")
	private Integer timeCost;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "创建者id")
	private String creatorId;

	@ApiModelProperty(value = "创建者名称")
	private String creatorName;

	@ApiModelProperty(value = "客户端类型")
	private Integer appType;

	@ApiModelProperty(value = "是否实体")
	private Integer isActual;
}
