package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_db_tool_category")
public class SituationDbToolCategory extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("工具分类名称")
	private String name;

	@ApiModelProperty()
	private String createUserId;

}
