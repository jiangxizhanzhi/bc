package com.cyberpeace.situation.dao.entity;

import java.util.HashMap;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.situation.constant.dashboard.CustomCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * @Author xuxz
 * @Date 11/11/2022 上午10:47
 */
@Data
@TableName("situation_dashboard_template")
public class DashboardTemplate extends BaseTable {

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

	@ApiModelProperty("颜色主题")
	private String topic;

	@ApiModelProperty("组件配置")
	private String componentsSetting;

	@ApiModelProperty("图层信息")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String layerShow;

	@ApiModelProperty("视角配置")
	private String viewAngle;

	@ApiModelProperty("刷新频率")
	private Integer refresh;

	@ApiModelProperty("模板配置")
	private String config;

	@ApiModelProperty("定制类型")
	private Integer customCode = CustomCode.ZB.getCode();

	@ApiModelProperty("编辑状态")
	private Boolean editStatus;

	@ApiModelProperty("创建人")
	private String createUserId;

	public enum DashboardTemplateLevel {

		TOPOLOGY(2, "拓扑级"), PARK(3, "园区级"), GIS(1, "GIS级"), BASIC_GIS(0, "基础模板");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		DashboardTemplateLevel(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public static HashMap<Integer, String> getDashboardTemplateLevel() {
		HashMap<Integer, String> map = new HashMap();
		map.put(DashboardTemplateLevel.GIS.code, DashboardTemplateLevel.GIS.desc);
		map.put(DashboardTemplateLevel.PARK.code, DashboardTemplateLevel.PARK.desc);
		map.put(DashboardTemplateLevel.TOPOLOGY.code, DashboardTemplateLevel.TOPOLOGY.desc);
		map.put(DashboardTemplateLevel.BASIC_GIS.code, DashboardTemplateLevel.BASIC_GIS.desc);
		return map;
	}

}
