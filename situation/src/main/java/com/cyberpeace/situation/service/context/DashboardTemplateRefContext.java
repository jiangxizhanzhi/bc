package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardTemplateRefContext implements Serializable {

	@ApiModelProperty("模板ID")
	private String resourceId;

	@ApiModelProperty("模板名称")
	private String name;

	@ApiModelProperty("模板类型")
	private String type;

	@ApiModelProperty("模板级别")
	private Integer level;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("画布高度")
	private String height;

	@ApiModelProperty("画布宽度")
	private String width;

	@ApiModelProperty("填充色")
	private String bgColor;

	@ApiModelProperty("编辑状态")
	private Boolean editStatus;

	@ApiModelProperty("背景主题")
	private String topic;

	@ApiModelProperty("视角配置")
	private ViewAngle viewAngle;

	@ApiModelProperty("刷新频率")
	private Integer refresh;

	@ApiModelProperty("组件配置")
	private String componentsSetting;

	public void setViewAngle(String viewAngle) {
		this.viewAngle = JsonUtil.toJsonObject(viewAngle, ViewAngle.class);

	}
}
