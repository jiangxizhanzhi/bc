package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 态势导调响应
 *
 * @author myy@date 2024/11/12
 */
@Data
public class SituationGuideRsp implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty("房间id")
	@NotBlank
	private String roomId;

	/**
	 * 业务类型
	 */
	@ApiModelProperty("训练类型 3:团队 4:红蓝")
	@NotNull
	private Integer businessType;

	/**
	 * 事件名称
	 */
	@ApiModelProperty("动效方案名称")
	@NotBlank
	private String eventName;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型 1:任务导调 2:手动导调")
	@NotNull
	private Integer type;

	/**
	 * 类型名称
	 */
	@ApiModelProperty("类型名称")
	private String typeName;

	/**
	 * 事件类型
	 */
	@ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
	@NotNull
	private Integer eventType;

	/**
	 * 事件类型名称
	 */
	@ApiModelProperty("事件类型名称")
	private String eventTypeName;

	/**
	 * 类别
	 */
	@ApiModelProperty("攻防类型")
	private Integer category;

	/**
	 * 类别名称
	 */
	@ApiModelProperty("攻防类型")
	private String categoryName;

	/**
	 * 源
	 */
	@ApiModelProperty("靶标源")
	private String source;

	/**
	 * 源名称
	 */
	@ApiModelProperty("靶标源名称")
	private String sourceName;

	/**
	 * 目标
	 */
	@ApiModelProperty("靶标目标")
	private String target;

	/**
	 * 目标名称
	 */
	@ApiModelProperty("靶标目标名称")
	private String targetName;

	/**
	 * 结果
	 */
	@ApiModelProperty("结果 1:成功 2失败")
	private Integer result;

	/**
	 * 结果名称
	 */
	@ApiModelProperty("结果名称")
	private String resultName;

	/**
	 * 成功 URL
	 */
	@ApiModelProperty("成功链接")
	private String successUrl;

	/**
	 * 失败 URL
	 */
	@ApiModelProperty("失败链接")
	private String failUrl;

	/**
	 * 红色 URL
	 */
	@ApiModelProperty("红队链接")
	private String redUrl;

	/**
	 * 蓝色 URL
	 */
	@ApiModelProperty("蓝队链接")
	private String blueUrl;

	/**
	 * 岗位
	 */
	@ApiModelProperty("归属角色 4:红队 5蓝队")
	private Integer role;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty("归属角色名称")
	private String roleName;

	/**
	 * 任务id
	 */
	@ApiModelProperty("任务id")
	private String missionId;

	/**
	 * 触发条件
	 */
	@ApiModelProperty("触发条件1:任务提交 2:任务判断成功")
	private Integer triggerCondition;

	/**
	 * 触发名称
	 */
	@ApiModelProperty("触发条件名称")
	private String triggerName;

}
