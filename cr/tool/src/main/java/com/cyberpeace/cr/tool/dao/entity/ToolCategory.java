package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.constant.ModelConstant;
import lombok.Data;

import java.io.Serializable;

@TableName("cr_tool_toolcategory")
@Data
public class ToolCategory implements Serializable {

	private Integer id;

	private Integer status = ModelConstant.NOT_DELETE;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String cnName;

	private String enName;

	private static final long serialVersionUID = 1L;
}