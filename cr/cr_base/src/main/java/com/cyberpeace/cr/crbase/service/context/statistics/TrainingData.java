package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练数据
 * 训练实例数据
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingData implements Serializable {

	@ApiModelProperty(value = "训练类型")
	private Integer appId;

	@ApiModelProperty(value = "类型名称")
	private String appName;

	@ApiModelProperty(value = "实例id")
	private String resourceId;

	@ApiModelProperty(value = "用户角色")
	private Integer roleGroup;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "")
	private String logo;

	@ApiModelProperty(value = "训练实例名称")
	private String name;

	@ApiModelProperty(value = "持续时间")
	private Integer durationTime;

	@ApiModelProperty(value = "难度")
	private Integer difficulty;
}
