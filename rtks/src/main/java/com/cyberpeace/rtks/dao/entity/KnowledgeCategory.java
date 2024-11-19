package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识点类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "rtks_knowledge_category")
@Data
public class KnowledgeCategory extends BaseTable {

	/**
	 * 知识点类别id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("知识点类别id")
	private String resourceId;

	/**
	 * 知识点类别名称
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点类别名称")
	private String content;
}