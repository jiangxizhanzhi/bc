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
 * 大屏模板返回
 *
 * @Author xuxz
 * @Date 11/11/2022 下午5:24
 */
@Data
public class DashboardTemplateRes implements Serializable {

	/**
	 * 模板资源id
	 */
	@ApiModelProperty("模板资源id")
	private String resourceId;

	/**
	 * 模板名称
	 */
	@ApiModelProperty("模板名称")
	private String name;

	/**
	 * 模板类型
	 */
	@ApiModelProperty("模板类型")
	private String type;

	/**
	 * 模板级别
	 */
	@ApiModelProperty("模板级别")
	private Integer level;

	/**
	 * 缩略图
	 */
	@ApiModelProperty("缩略图")
	private String thumbnail;

	/**
	 * 画布高度
	 */
	@ApiModelProperty("画布高度")
	private String height;

	/**
	 * 画布宽度
	 */
	@ApiModelProperty("画布宽度")
	private String width;

	/**
	 * 背景类型
	 */
	@ApiModelProperty("背景类型")
	private String bgType;

	/**
	 * 背景类型
	 */
	@ApiModelProperty("背景类型")
	private String bgTemplate;

	/**
	 * 背景图片
	 */
	@ApiModelProperty("背景图片")
	private String bgImg;

	/**
	 * 填充色
	 */
	@ApiModelProperty("填充色")
	private String bgColor;

	/**
	 * 背景主题
	 */
	@ApiModelProperty("背景主题")
	private String topic;

	/**
	 * 组件配置信息
	 */
	@ApiModelProperty("组件配置信息")
	private List<DashboardComponents> componentsSetting;

	/**
	 * 图层信息
	 */
	@ApiModelProperty("图层信息")
	private List<LayerShowReq> layerShow;

	/**
	 * 视角配置
	 */
	@ApiModelProperty("视角配置")
	private ViewAngle viewAngle;

	/**
	 * 刷新频率
	 */
	@ApiModelProperty("刷新频率")
	private Integer refresh;

	/**
	 * 定制类型
	 */
	@ApiModelProperty("定制类型")
	private Integer customCode;

	/**
	 * 编辑状态
	 */
	@ApiModelProperty("编辑状态")
	private Boolean editStatus;

	/**
	 * 大屏参数配置
	 */
	@ApiModelProperty("大屏参数配置")
	private DashboardConfigContext config;

	/**
	 * 设置配置
	 *
	 * @param config 配置
	 */
	public void setConfig(String config) {
		this.config = JsonUtil.toJsonObject(config, DashboardConfigContext.class);
	}

	/**
	 * 设置视角
	 *
	 * @param viewAngle 视角
	 */
	public void setViewAngle(String viewAngle) {
		this.viewAngle = JsonUtil.toJsonObject(viewAngle, ViewAngle.class);
	}

	/**
	 * 设置显示层
	 *
	 * @param layerShow 显示层
	 */
	public void setLayerShow(String layerShow) {
		this.layerShow = JsonUtil.toJsonObjectArray(layerShow, LayerShowReq.class);
	}
}
