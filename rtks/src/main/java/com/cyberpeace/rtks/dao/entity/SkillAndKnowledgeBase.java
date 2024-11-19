package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能和知识点基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
public class SkillAndKnowledgeBase extends BaseTable {

	/**
	 * 知识点id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("知识点id")
    private String resourceId;

	/**
	 * 知识点类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点类别id")
    private String categoryId;

	/**
	 * 知识点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点名称")
    private String content;

	/**
	 * 知识点关联课程
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点关联课程")
    private String courses;

	/**
	 * 知识点权重
	 *
	 * @see Integer
	 */
	@TableField(exist = false)
    @ApiModelProperty("知识点权重")
    private Integer weight;

}
