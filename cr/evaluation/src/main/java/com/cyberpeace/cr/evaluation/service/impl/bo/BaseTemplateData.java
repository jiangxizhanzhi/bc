package com.cyberpeace.cr.evaluation.service.impl.bo;

import com.cyberpeace.common.context.user.OperationUserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaseTemplateData implements Serializable {

	@ApiModelProperty("试验名称")
	private String name;

	@ApiModelProperty("试验目标")
	private String target;

	@ApiModelProperty("logoId")
	private String logoId;

	@ApiModelProperty("logo路径")
	private String logo;

	@ApiModelProperty("试验条件")
	private String condition;

	@ApiModelProperty("试验方法")
	private String method;

	@ApiModelProperty("测试类型")
	private Integer type;

	@ApiModelProperty("任务方案附件")
	private String scheme;

	@ApiModelProperty("任务方案附件路径")
	private String schemePath;

	@ApiModelProperty("任务方案附件名称")
	private String schemeName;

	@ApiModelProperty("授权用户id")
	private List<String> authUserIds;

	@ApiModelProperty("授权用户")
	private List<OperationUserInfo> operationAuthIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	private Integer operationAuthType;

	@ApiModelProperty("体系ID")
	private String systemId;
}
