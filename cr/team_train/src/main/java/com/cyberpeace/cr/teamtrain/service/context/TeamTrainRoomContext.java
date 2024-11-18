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
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class TeamTrainRoomContext implements Serializable {

	/**
     * 资源id
     *
     * @see String
     */
	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	/**
     * 模板
     *
     * @see String
     */
	@ApiModelProperty(required = true)
	@NotNull
	private String template;

	/**
     * 类别
     *
     * @see Integer
     */
	@ApiModelProperty(required = true)
	@NotNull
	private Integer category;

	/**
     * 名称
     *
     * @see String
     */
	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	/**
     * 预技能
     *
     * @see List<String>
     */
	@ApiModelProperty(required = true)
	@NotNull
	private List<String> preSkill;

	/**
     * 训练技能
     *
     * @see List<String>
     */
	@ApiModelProperty(required = true)
	@NotNull
	private List<String> trainSkill;

	/**
     * 难
     *
     * @see Integer
     */
	@ApiModelProperty(required = true)
	@NotNull
	private Integer difficult;

	/**
     * 开始时间
     *
     * @see Date
     */
	@ApiModelProperty(required = true)
	@NotNull
	private Date startTime;

	/**
     * 结束时间
     *
     * @see Date
     */
	@ApiModelProperty(required = true)
	@NotNull
	private Date endTime;

	/**
     * 描述
     *
     * @see String
     */
	@ApiModelProperty(required = true)
	@NotNull
	private String description;

	/**
     * 角色配置
     *
     * @see List<RoomRole>
     */
	@ApiModelProperty(required = true)
	@NotNull
	private List<RoomRole> roleConfig;

	/**
     * 启用录制
     *
     * @see Boolean
     */
	private Boolean enableRecording;

	/**
     * 图标
     *
     * @see String
     */
	private String logo;
}
