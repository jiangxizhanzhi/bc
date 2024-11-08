package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务方案模板响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MissionSchemeTemplateRsp {

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板类型")
	private Integer template;

	/**
	 * 战斗配置
	 */
	@ApiModelProperty(value = "方案配置")
	private List<MissionSchemeStage> combatConfig;

	/**
	 * 配置
	 */
	@ApiModelProperty(value = "方案配置json")
	private String config;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建者id")
	private String createUser;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 代表名称
	 */
	@ApiModelProperty(value = "创建者姓名")
	private String repName;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "名称")
	private String title;
}
