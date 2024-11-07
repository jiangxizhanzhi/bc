package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统
 *
 * @author SN
 * @date 2024/11/07
 */
@TableName(value = "cr_eval_system")
@Data
public class EvalSystem extends BaseTable {

	/**
	 * 资源id
	 */
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

	/**
	 * 第一指数数量
	 */
	@ApiModelProperty("一级指标数量")
	private Integer firstIndexNum;

	/**
	 * 第二指数数量
	 */
	@ApiModelProperty("二级指标数量")
	private Integer secondIndexNum;

	/**
	 * 第三指数数量
	 */
	@ApiModelProperty("三级指标数量")
	private Integer thirdIndexNum;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建者ID")
	private String createUserId;
}