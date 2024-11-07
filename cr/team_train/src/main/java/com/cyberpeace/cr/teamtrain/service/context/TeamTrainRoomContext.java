package com.cyberpeace.cr.teamtrain.service.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练实例模型
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamTrainRoomContext implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	/**
	 * 模板
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String template;

	/**
	 * 类别
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private Integer category;

	/**
	 * 名称
	 */
	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	/**
	 * 预技能
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private List<String> preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private List<String> trainSkill;

	/**
	 * 难
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private Integer difficult;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private Date endTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String description;

	/**
	 * 角色配置
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private List<RoomRole> roleConfig;

	/**
	 * 启用录制
	 */
	private Boolean enableRecording;

	/**
	 * 图标
	 */
	private String logo;
}
