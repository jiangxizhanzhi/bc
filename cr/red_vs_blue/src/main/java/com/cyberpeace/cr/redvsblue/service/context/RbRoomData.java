package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 红蓝实例数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbRoomData implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 类别
	 */
	private Integer category;

	/**
	 * 前置技能
	 */
	private String preSkill;

	/**
	 * 训练技能
	 */
	private String trainSkill;

	/**
	 * 启动时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 模板
	 */
	private String template;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 难度
	 */
	private Integer difficult;

	/**
	 * 已复制拓扑id
	 */
	private String copyedTopoId;

	/**
	 * 训练岗位数据
	 */
	private String trainingRoleData;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 场景启动时间
	 */
	private Date sceneStartTime;

	/**
	 * 场景结束时间
	 */
	private Date sceneEndTime;

	/**
	 * 训练状态
	 */
	private Integer trainingStatus;

	/**
	 * 运行
	 */
	private Integer runtime;

	/**
	 * 启用录制
	 */
	private Boolean enableRecording;

	/**
	 * 支持部分启动
	 */
	private Boolean supportPartialStart;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;
}
