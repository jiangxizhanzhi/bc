package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练实例数据
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingData implements Serializable {

	/**
	 * 应用程序id
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer appId;

	/**
	 * 应用程序名称
	 */
	@ApiModelProperty(value = "类型名称")
	private String appName;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "实例id")
	private String resourceId;

	/**
	 * 州
	 */
	@ApiModelProperty(value = "训练状态")
	private Integer state;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "用户角色")
	private Integer roleGroup;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 子类型
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer subType;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "")
	private String logo;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "训练实例名称")
	private String name;

	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "持续时间")
	private Integer durationTime;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficulty;
}
