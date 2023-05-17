package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午4:26
 */
@TableName("situation_dynamic_effects_mapping")
@Data
public class DynamicEffectsMapping extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("动效名称")
	private String name;

	@ApiModelProperty("动效类型")
	private Integer type;

	@ApiModelProperty("攻击方式")
	private String attackType;

	@ApiModelProperty("源节点动效")
	private String sourceNodeDynamic;

	@ApiModelProperty("目标节点动效")
	private String targetNodeDynamic;

	private String createUserId;

	@ApiModelProperty("聚焦")
	private Boolean focus;

	@ApiModelProperty("颜色")
	private String color;

	@ApiModelProperty("线形")
	private String line;

	@ApiModelProperty("线形大小")
	private Integer lineSize;
}
