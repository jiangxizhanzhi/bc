
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_source_analyze_rule")
public class SpaceSourceAnalyzeRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则列表")
	private String rule;
}