package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间流量检测统治
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_traffic_detect_rule")
public class SpaceTrafficDetectRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则内容")
	private String ruleContent;

	@ApiModelProperty(value = "规则编号")
	private String ruleId;

	@ApiModelProperty(value = "规则信息")
	private String ruleInfo;

	@ApiModelProperty(value = "威胁等级")
	private String threatLevel;

	@ApiModelProperty(value = "工具")
	private String tool;

	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	@ApiModelProperty(value = "攻击类型， type=58")
	private Integer attackType;


	public SpaceTrafficDetectRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTrafficDetectRuleBo bo = BeanUtils.copy(this, SpaceTrafficDetectRuleBo.class);
		return bo;
	}
}
