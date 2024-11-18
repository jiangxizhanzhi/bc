package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务方案模板响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionSchemeTemplateRsp {

	/**
	 * 颜色
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 模板
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "模板类型")
	private Integer template;

	/**
	 * 战斗配置
	 *
	 * @see List<MissionSchemeStage>
	 */
	@ApiModelProperty(value = "方案配置")
	private List<MissionSchemeStage> combatConfig;

	/**
	 * 配置
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "方案配置json")
	private String config;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "创建者id")
	private String createUser;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 代表名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "创建者姓名")
	private String repName;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String title;
}
