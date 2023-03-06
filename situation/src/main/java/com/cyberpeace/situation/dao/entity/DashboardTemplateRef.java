package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 11/11/2022 上午10:47
 */
@Data
@TableName("situation_dashboard_template_ref")
public class DashboardTemplateRef extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
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

	@ApiModelProperty("组件配置")
	private String componentsSetting;

	@ApiModelProperty("图层信息")
	private String layerShow;

	@ApiModelProperty("视角配置")
	private String viewAngle;

	@ApiModelProperty("刷新频率")
	private Integer refresh;

	@ApiModelProperty("定制类型")
	private Integer customCode;

	@ApiModelProperty("编辑状态")
	private Boolean editStatus;

	@ApiModelProperty("原始模板ID")
	private String originTemplateId;

}
