package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 多表关联的团队实例
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamRoomData implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	private Integer category;

	/**
	 * 预技能
	 *
	 * @see String
	 */
	private String preSkill;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	private String trainSkill;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 模板
	 *
	 * @see String
	 */
	private String template;

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	private String templateName;

	/**
	 * 难
	 *
	 * @see Integer
	 */
	private Integer difficult;

	/**
	 * 训练角色数据
	 *
	 * @see String
	 */
	private String trainingRoleData;

	/**
	 * 已复制拓扑id
	 *
	 * @see String
	 */
	private String copyedTopoId;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	private Integer durationTime;

	/**
	 * 场景开始时间
	 *
	 * @see Date
	 */
	private Date sceneStartTime;

	/**
	 * 场景结束时间
	 *
	 * @see Date
	 */
	private Date sceneEndTime;

	/**
	 * 训练状态
	 *
	 * @see Integer
	 */
	private Integer trainingStatus;

	/**
	 * 运行
	 *
	 * @see Integer
	 */
	private Integer runtime;

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

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

}
