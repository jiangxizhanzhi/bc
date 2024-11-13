package com.cyberpeace.cr.crbase.service.context.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 屏幕Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class ScreenBo implements Serializable {

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private String endTime;

	/**
	 * 事件名称
	 */
	@ApiModelProperty("事件名称")
	private String eventName;

	/**
	 * 类别
	 */
	@ApiModelProperty("攻防类型")
	private Integer category;

	/**
	 * 源
	 */
	@ApiModelProperty("攻击源")
	private String source;

	/**
	 * 目标
	 */
	@ApiModelProperty("攻击目标")
	private String target;

	/**
	 * 结果
	 */
	@ApiModelProperty("攻击结果")
	private Integer result;

	/**
	 * 岗位
	 */
	@ApiModelProperty("归属角色")
	private Integer role;

}
