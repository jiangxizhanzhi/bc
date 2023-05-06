package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务计划模板返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionSchemeTemplateRsp {

	@ApiModelProperty(value = "色调")
	private Integer color;

	@ApiModelProperty(value = "模板类型")
	private Integer template;

	@ApiModelProperty(value = "方案配置")
	private List<MissionSchemeStage> combatConfig;

	@ApiModelProperty(value = "方案配置json")
	private String config;

	@ApiModelProperty(value = "创建者id")
	private String createUser;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "创建者姓名")
	private String repName;

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String title;
}
