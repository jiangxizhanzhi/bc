package com.cyberpeace.cr.tool.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工具类别
 *
 * @author SN
 * @date 2024/11/08
 */
@TableName("cr_tool_toolcategory")
@Getter
@Setter
public class ToolCategory implements Serializable, SelectI18nName {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 资源id
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
	 * 中文名称
	 */
	@ApiModelProperty("英文名称")
	private String enName;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 串行版本 UID
	 */
	private static final long serialVersionUID = 1L;
}