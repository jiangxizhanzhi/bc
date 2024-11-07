package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB时间管理响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbTimeManagementRsp extends BaseTable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "时间管理id")
	private String resourceId;

	/**
	 * 红色 vs 蓝色
	 */
	@ApiModelProperty(value = "房间id")
	private String redVsBlue;

	/**
	 * 天文时间
	 */
	@ApiModelProperty(value = "天文时间")
	private Date astronomicalTime;

	/**
	 * 战斗时间
	 */
	@ApiModelProperty(value = "作战时间")
	private Date combatTime;

	/**
	 * 率
	 */
	@ApiModelProperty(value = "速率")
	private Integer rate;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer status;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建者id")
	private String createUser;

	/**
	 * 修改用户
	 */
	@ApiModelProperty(value = "修改者id")
	private String modifyUser;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date modifyTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/**
	 * 当前战斗时间
	 */
	@ApiModelProperty(value = "当前作战时间")
	private Date currentCombatTime;

	/**
	 * 当前天文时间
	 */
	@ApiModelProperty(value = "当前天文时间")
	private Date currentAstronomicalTime;

}
