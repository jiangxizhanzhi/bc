package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName rtks_knowledge_category
 */
@TableName(value = "rtks_knowledge_category")
@Data
public class KnowledgeCategory extends BaseTable {

	/**
	 * 知识点类别id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("知识点类别id")
	private String resourceId;

	/**
	 * 知识点类别名称
	 */
	@ApiModelProperty("知识点类别名称")
	private String content;
}