package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_eval_result")
@Data
public class EvalResult extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 评估运行实例id
	 *
	 * @see String
	 */
	private String record;

	/**
	 * 一级指标得分
	 *
	 * @see Integer
	 */

	private Integer firstIndexRes;

	/**
	 * 二级指标得分
	 *
	 * @see String
	 */
	private String secondIndexRes;

	/**
	 * 三级指标得分
	 *
	 * @see String
	 */
	private String thirdIndexRes;

	/**
	 * 四级指标得分
	 *
	 * @see String
	 */
	private String fourthIndexRes;

	/**
	 * 五级指标得分
	 *
	 * @see String
	 */
	private String fifthIndexRes;

	/**
	 * 执行轮次
	 *
	 * @see Integer
	 */
	private Integer exeCycle;


}