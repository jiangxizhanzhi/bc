package com.cyberpeace.cr.tool.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具类别
 *
 * @author SN
 * @date 2024/11/14
 */
@TableName("cr_tool_toolcategory")
@Data
public class ToolCategory implements Serializable, SelectI18nName {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 中文名称
	 */
	@ApiModelProperty("中文名称")
	private String cnName;

	/**
	 * 英文名称
	 */
	@ApiModelProperty("英文名称")
	private String enName;

	/**
	 * 串行版本 UID
	 */
	private static final long serialVersionUID = 1L;
}