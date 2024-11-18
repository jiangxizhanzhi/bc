package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队房间响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamRoomRsp implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类别")
	private Integer category;

	/**
	 * 预技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	/**
	 * 模板
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模板id")
	private String template;

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模板名称")
	private String templateName;

	/**
	 * 难
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * 训练角色数据
	 *
	 * @see BaseRole
	 */
	@ApiModelProperty(value = "自己岗位，基本信息，查列表会查询其他学员角色的内容列表")
	private BaseRole trainingRoleData;

	/**
	 * 角色数据
	 *
	 * @see List<TeamRoomRoleRes>
	 */
	@ApiModelProperty(value = "所有岗位(包含教员)，单独查时使用")
	private List<TeamRoomRoleRes> rolesData;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	/**
	 * 场景开始时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "场景开启时间")
	private Date sceneStartTime;

	/**
	 * 场景结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "场景关闭时间")
	private Date sceneEndTime;

	/**
	 * 训练状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练状态")
	private Integer trainingStatus;

	/**
	 * 运行
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "运行时间")
	private Integer runtime;

	/**
	 * 节点计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "后台模板id，用于查询职业框架关联")
	private String businessTemplateId;

	/**
	 * 与攻击有关
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否关联 ATT&CK")
	private Boolean isAttackRelated;

	/**
	 * 攻击计数
	 *
	 * @see List<NameCount>
	 */
	@ApiModelProperty(value = "关联 ATT&CK 计数")
	private List<NameCount> attackCount;

	/**
	 * 是否与 IC 相关
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否关联 ATT&CK ICS")
	private Boolean isIcsRelated;

	/**
	 * 集成电路计数
	 *
	 * @see List<NameCount>
	 */
	@ApiModelProperty(value = "关联 ATT&CK ICS 计数")
	private List<NameCount> icsCount;

	/**
	 * 与 engage 相关
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否关联 Engage")
	private Boolean isEngageRelated;

	/**
	 * 资源占用数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 聘计数
	 *
	 * @see List<NameCount>
	 */
	@ApiModelProperty(value = "关联 Engage 计数")
	private List<NameCount> engageCount;
}
