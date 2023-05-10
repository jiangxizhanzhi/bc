package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName cr_eval_system
 */
@TableName(value = "cr_eval_system")
@Data
public class EvalSystem extends BaseTable {

	@ApiModelProperty("ID")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 连接线
	 */
	@ApiModelProperty("连线信息")
	private String connlist;

	@ApiModelProperty("一级指标数量")
	private Integer firstIndexNum;

	@ApiModelProperty("二级指标数量")
	private Integer secondIndexNum;

	@ApiModelProperty("三级指标数量")
	private Integer thirdIndexNum;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建者ID")
	private String createUserId;

	/**
	 * 矩阵内容
	 */
	private String matrixData;
}