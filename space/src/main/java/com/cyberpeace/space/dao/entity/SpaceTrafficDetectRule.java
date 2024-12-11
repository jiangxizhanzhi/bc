package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
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


	public SpaceTrafficDetectRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTrafficDetectRuleBo bo = BeanUtils.copy(this, SpaceTrafficDetectRuleBo.class);
		bo.setTool(JsonUtil.toJsonObjectArray(this.tool, String.class));
		return bo;
	}
}
