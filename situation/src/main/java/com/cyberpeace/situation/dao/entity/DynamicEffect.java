package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_dynamic_effects")
public class DynamicEffect extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("动效名称")
	private String name;

	@ApiModelProperty("动效分组")
	private String category;

	@ApiModelProperty("动效类型 1-预置 2-用户上传")
	private Integer type;

	@ApiModelProperty("预置动作类型")
	private Integer actionType;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("源节点动效文件")
	private String dynamicFile;

	@ApiModelProperty("源文件动效文件目录")
	private String dynamicDir;

	private String createUserId;

	public void setSourceNodeDynamicFile(String dynamicFile) {
		this.dynamicFile = dynamicFile;
	}

}
