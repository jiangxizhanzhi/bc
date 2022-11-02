package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situational_park_resource_library")
public class DynamicEffectsLibrary extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("动效名称")
	private String name;

	@ApiModelProperty("动效类型")
	private String dynamicEffectsLibraryCategory;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("源节点动效文件")
	private String sourceNodeDynamicFile;

	@ApiModelProperty("目标节点动效文件")
	private String targetNodeDynamicFile;

	@ApiModelProperty("聚焦")
	private String focus;

	@ApiModelProperty("颜色")
	private String color;

	@ApiModelProperty("线形")
	private String line;

	@ApiModelProperty("线形大小")
	private String lineSize;


	private String createUserId;

}
