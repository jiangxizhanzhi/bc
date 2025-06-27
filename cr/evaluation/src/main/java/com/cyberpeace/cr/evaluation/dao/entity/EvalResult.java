package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 测评结果
 *
 * @author SN
 * @date 2024/11/14
 */
@TableName(value = "cr_eval_result")
@Data
public class EvalResult extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 评估运行实例id
	 */
	@TableField("`record`")
	private String record;

	/**
	 * 一级指标得分
	 */

	private Integer firstIndexRes;

	/**
	 * 二级指标得分
	 */
	private String secondIndexRes;

	/**
	 * 三级指标得分
	 */
	private String thirdIndexRes;

	/**
	 * 四级指标得分
	 */
	private String fourthIndexRes;

	/**
	 * 五级指标得分
	 */
	private String fifthIndexRes;

	/**
	 * 执行轮次
	 */
	private Integer exeCycle;


}