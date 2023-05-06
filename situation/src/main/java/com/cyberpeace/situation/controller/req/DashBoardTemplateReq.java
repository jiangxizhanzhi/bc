package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.constant.gis.MapView;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.context.config.DashboardConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午3:29
 */
@Data
public class DashBoardTemplateReq implements Serializable {

	@ApiModelProperty("模板资源id")
	@NotBlank(groups = Update.class, message = ErrorCode.METHOD_ARG_NOT_VALID)
	private String resourceId;

	@ApiModelProperty("模板名称")
	@NotBlank(groups = { Update.class, Create.class })
	private String name;

	@ApiModelProperty("模板类型")
	@NotBlank(groups = { Update.class, Create.class })
	private String type;

	@ApiModelProperty("模板级别")
	private Integer level;

	@ApiModelProperty("缩略图")
	@NotBlank(groups = { Update.class, Create.class })
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
	private List<DashboardComponentsReq> componentsSetting;

	@ApiModelProperty("图层信息")
	private List<LayerShowReq> layerShow;

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle = MapView.BJ.getViewAngle();

	@ApiModelProperty("刷新频率")
	private Integer refresh;

	@ApiModelProperty("定制类型")
	private Integer customCode;

	@ApiModelProperty("大屏参数配置")
	private DashboardConfigContext config;
}
