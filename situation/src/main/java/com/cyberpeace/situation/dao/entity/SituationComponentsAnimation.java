package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_components_animation")
public class SituationComponentsAnimation extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("动画名称")
	private String name;

	@ApiModelProperty("二维组件id")
	private String chartId;

	private String description;

	private String config;

	private String createUserId;

}
