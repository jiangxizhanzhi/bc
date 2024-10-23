
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_loaded_file_threat_rule")
public class SpaceLoadedFileThreatRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则内容")
	private String content;

	@ApiModelProperty(value = "规则描述")
	private String description;
}