package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MpLog implements Serializable {

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("日志来源")
	private String from;

	@ApiModelProperty("日志类型")
	private Integer log_type;

	@ApiModelProperty("操作人IP")
	private String op_ip;

	@ApiModelProperty("模块")
	private Integer op_module;

	@ApiModelProperty("操作对象")
	private String op_target;

	@ApiModelProperty("操作类型")
	private Integer op_type;

	@ApiModelProperty("操作主题")
	private String title;

	@ApiModelProperty("用户ID")
	private String user_id;

	@ApiModelProperty("用户名称")
	private String user_name;

	@ApiModelProperty("用户类型（用户，客户端）")
	private String user_type;
}
