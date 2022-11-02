package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("situational_two_dimensional_components")
public class TwoDimensionalComponents extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("组件名称")
	private String name;

	@ApiModelProperty("图表类型")
	private Integer type;

	private String description;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("组件代码")
	private String componentCode;

	private String createUserId;

	public enum Type implements GenericEnum {

		LINE_CHART(0, "折线图"), HISTOGRAM(1, "柱状图"), PIE_CHART(2, "饼图"),
		SCATTER_HISTOGRAM(4, "散点图"), RADAR_MAP(5, "雷达图"), SANKEY_DIAGRAM(6, "桑基图"),
		FUNNEL_FIGURE(7, "漏斗图"),SUN_FIGURE(8, "旭日图");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
