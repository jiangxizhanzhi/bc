package com.cyberpeace.cr.tool.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;
import lombok.Data;

@TableName("cr_tool_toolcategory")
@Data
public class ToolCategory implements Serializable, SelectI18nName {

	private Integer id;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String cnName;

	private String enName;

	private static final long serialVersionUID = 1L;
}