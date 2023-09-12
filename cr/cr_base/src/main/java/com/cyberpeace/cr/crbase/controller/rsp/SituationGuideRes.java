package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SituationGuideRes implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("动效方案名称")
	@NotBlank
	private String name;

	@ApiModelProperty("类型 1:任务导调 2:手动导调")
	@NotBlank
	private Integer type;

	@ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
	@NotBlank
	private Integer eventType;

	@ApiModelProperty("攻防类型")
	private Integer category;

	@ApiModelProperty("攻击源")
	private String source;

	@ApiModelProperty("攻击目标")
	private String target;

	@ApiModelProperty("攻击结果 1:成功 2失败")
	private Integer result;

	@ApiModelProperty("任务id")
	private String missionId;

	@ApiModelProperty("触发条件1:任务提交 2:任务判断成功")
	private Integer condition;

}
