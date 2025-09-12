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
 * @date 2024/11/14
 */
@Data
public class TeamRoomRsp  {

	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

    @ApiModelProperty(value = "xd类型")
    private Integer xdType;

    /**
     * 持续时长
     */
    @ApiModelProperty(value = "持续时长")
    private Integer durationTime;

    /**
     * 难度
     */
    @ApiModelProperty(value = "难度")
    private Integer difficult;

    @ApiModelProperty(value = "训练类别")
    private String trainXdType;

    @ApiModelProperty(value = "训练子类型")
    private String trainXdSubType;


    /**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别")
	private Integer category;

	/**
	 * 前置技能
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练目标
	 */
	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id")
	private String template;

	/**
	 * 模板名称
	 */
	@ApiModelProperty(value = "模板名称")
	private String templateName;



	/**
	 * 自己岗位，基本信息，查列表会查询其他学员角色的内容列表
	 */
	@ApiModelProperty(value = "自己岗位，基本信息，查列表会查询其他学员角色的内容列表")
	private BaseRole trainingRoleData;

	/**
	 * 所有岗位(包含教员)，单独查时使用
	 */
	@ApiModelProperty(value = "所有岗位(包含教员)，单独查时使用")
	private List<TeamRoomRoleRes> rolesData;



	/**
	 * 场景开启时间
	 */
	@ApiModelProperty(value = "场景开启时间")
	private Date sceneStartTime;

	/**
	 * 场景关闭时间
	 */
	@ApiModelProperty(value = "场景关闭时间")
	private Date sceneEndTime;

	/**
	 * 训练状态
	 */
	@ApiModelProperty(value = "训练状态")
	private Integer trainingStatus;

	/**
	 * 运行时间
	 */
	@ApiModelProperty(value = "运行时间")
	private Integer runtime;

	/**
	 * 节点数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 录屏开关
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	/**
	 * LOGO
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 后台模板id，用于查询职业框架关联
	 */
	@ApiModelProperty(value = "后台模板id，用于查询职业框架关联")
	private String businessTemplateId;

	/**
	 * 是否关联 ATT&CK
	 */
	@ApiModelProperty(value = "是否关联 ATT&CK")
	private Boolean isAttackRelated;

	/**
	 * 关联 ATT&CK 计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK 计数")
	private List<NameCount> attackCount;

	/**
	 * 是否关联 ATT&CK ICS
	 */
	@ApiModelProperty(value = "是否关联 ATT&CK ICS")
	private Boolean isIcsRelated;

	/**
	 * 关联 ATT&CK ICS 计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK ICS 计数")
	private List<NameCount> icsCount;

	/**
	 * 是否关联 Engage
	 */
	@ApiModelProperty(value = "是否关联 Engage")
	private Boolean isEngageRelated;

	/**
	 * 拓扑中占用的授权节点数量
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 关联 Engage 计数
	 */
	@ApiModelProperty(value = "关联 Engage 计数")
	private List<NameCount> engageCount;
}
