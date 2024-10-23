
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

}