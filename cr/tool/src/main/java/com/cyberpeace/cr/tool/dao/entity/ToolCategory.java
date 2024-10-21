package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@TableName("cr_tool_toolcategory")
@Data
public class ToolCategory implements Serializable, SelectI18nName {

	private Integer id;

	@ApiModelProperty("ID")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("中文名称")
	private String cnName;

	@ApiModelProperty("英文名称")
	private String enName;

	private String type;

	private static final long serialVersionUID = 1L;
}