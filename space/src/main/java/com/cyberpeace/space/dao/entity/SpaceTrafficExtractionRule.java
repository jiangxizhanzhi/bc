package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_traffic_extraction_rule")
public class SpaceTrafficExtractionRule extends SpaceBaseTable {

	@ApiModelProperty(value = "顺序")
	private String order;

	@ApiModelProperty(value = "文件名")
	private String filename;

	@ApiModelProperty(value = "路径")
	private String route;

}
