package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author myy
 */
@Data
public class SituationGuideRsp implements Serializable {

	@ApiModelProperty("房间id")
	@NotBlank
	private String roomId;

	@ApiModelProperty("训练类型 3:团队 4:红蓝")
	@NotNull
	private Integer businessType;

	@ApiModelProperty("动效方案名称")
	@NotBlank
	private String eventName;

	@ApiModelProperty("类型 1:任务导调 2:手动导调")
	@NotNull
	private Integer type;

	@ApiModelProperty("类型名称")
	private String typeName;

	@ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
	@NotNull
	private Integer eventType;

	@ApiModelProperty("事件类型名称")
	private String eventTypeName;

	@ApiModelProperty("攻防类型")
	private Integer category;

	@ApiModelProperty("攻防类型")
	private String categoryName;

	@ApiModelProperty("靶标源")
	private String source;

	@ApiModelProperty("靶标源名称")
	private String sourceName;

	@ApiModelProperty("靶标目标")
	private String target;

	@ApiModelProperty("靶标目标名称")
	private String targetName;

	@ApiModelProperty("结果 1:成功 2失败")
	private Integer result;

	@ApiModelProperty("结果名称")
	private String resultName;

	@ApiModelProperty("成功链接")
	private String successUrl;

	@ApiModelProperty("失败链接")
	private String failUrl;

	@ApiModelProperty("失败链接")
	private String redUrl;

	@ApiModelProperty("成功链接")
	private String blueUrl;

	@ApiModelProperty("归属角色 4:红队 5蓝队")
	private Integer role;

	@ApiModelProperty("归属角色名称")
	private String roleName;

	@ApiModelProperty("任务id")
	private String missionId;

	@ApiModelProperty("触发条件1:任务提交 2:任务判断成功")
	private Integer triggerCondition;

	@ApiModelProperty("触发条件名称")
	private String triggerName;

}
