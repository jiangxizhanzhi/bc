package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.constant.dashboard.CustomCode;
import com.cyberpeace.situation.constant.dashboard.DashboardLevel;
import com.cyberpeace.situation.constant.gis.MapView;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.context.config.DashboardConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏模板请求
 *
 * @Author xuxz
 * @Date 11/11/2022 下午3:29
 */
@Data
public class DashBoardTemplateReq implements Serializable {

	/**
	 * 模板ID
	 */
	@ApiModelProperty("模板ID")
	@NotBlank(groups = Update.class, message = ErrorCode.METHOD_ARG_NOT_VALID)
	private String resourceId;

	/**
	 * 模板名称
	 */
	@ApiModelProperty("模板名称")
	@NotBlank(groups = { Update.class, Create.class })
	private String name;

	/**
	 * 模板类型
	 */
	@ApiModelProperty("模板类型")
	@NotBlank(groups = { Update.class, Create.class })
	private String type;

	/**
	 * 模板级别
	 */
	@ApiModelProperty("模板级别")
	private Integer level = DashboardLevel.RW.getCode();

	/**
	 * 缩略图
	 */
	@ApiModelProperty("缩略图")
	@NotBlank(groups = { Update.class, Create.class })
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
	@ApiModelProperty("背景类型 0-填充色 1-背景图片 2-背景模板")
	private String bgType;

	/**
	 * 背景类型
	 */
	@ApiModelProperty("背景模板")
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
	@ApiModelProperty("大屏主题")
	private String topic;

	/**
	 * 组件配置信息
	 */
	@ApiModelProperty("组件配置信息")
	private List<DashboardComponentsReq> componentsSetting;

	/**
	 * 图层信息
	 */
	@ApiModelProperty("图层信息")
	private List<LayerShowReq> layerShow;

	/**
	 * 初始视角
	 */
	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle = MapView.BJ.getViewAngle();

	/**
	 * 刷新频率
	 */
	@ApiModelProperty("刷新频率")
	private Integer refresh;

	/**
	 * 定制类型
	 */
	@ApiModelProperty("定制类型")
	private Integer customCode = CustomCode.ZB.getCode();

	/**
	 * 大屏参数配置
	 */
	@ApiModelProperty("大屏参数配置")
	private DashboardConfigContext config;
}
