package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.LayerShowReq;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.context.config.DashboardConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午5:24
 */
@Data
public class DashboardTemplateRes implements Serializable {

	@ApiModelProperty("模板资源id")
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

	@ApiModelProperty("背景类型")
	private String bgType;

	@ApiModelProperty("背景类型")
	private String bgTemplate;

	@ApiModelProperty("背景图片")
	private String bgImg;

	@ApiModelProperty("填充色")
	private String bgColor;

	@ApiModelProperty("背景主题")
	private String topic;

	@ApiModelProperty("组件配置信息")
	private List<DashboardComponents> componentsSetting;

	@ApiModelProperty("图层信息")
	private List<LayerShowReq> layerShow;

	@ApiModelProperty("视角配置")
	private ViewAngle viewAngle;

	@ApiModelProperty("刷新频率")
	private Integer refresh;

	@ApiModelProperty("定制类型")
	private Integer customCode;

	@ApiModelProperty("编辑状态")
	private Boolean editStatus;

	@ApiModelProperty("大屏参数配置")
	private DashboardConfigContext config;

	public void setConfig(String config) {
		this.config = JsonUtil.toJsonObject(config, DashboardConfigContext.class);
	}

	public void setViewAngle(String viewAngle) {
		this.viewAngle = JsonUtil.toJsonObject(viewAngle, ViewAngle.class);
	}

	public void setLayerShow(String layerShow) {
		this.layerShow = JsonUtil.toJsonObjectArray(layerShow, LayerShowReq.class);
	}
}
