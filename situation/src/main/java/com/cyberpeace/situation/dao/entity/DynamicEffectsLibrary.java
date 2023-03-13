package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_dynamic_effects")
public class DynamicEffectsLibrary extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("动效名称")
	private String name;

	@ApiModelProperty("动效类型")
	private String category;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("源节点动效文件")
	private String sourceNodeDynamicFile;

	@ApiModelProperty("源文件动效文件目录")
	private String sourceNodeDynamicDir;

	private String createUserId;

}
