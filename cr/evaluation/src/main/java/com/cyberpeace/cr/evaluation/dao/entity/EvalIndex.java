package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 评估指数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_eval_index")
@Data
public class EvalIndex extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 指标级别：1、2、3
	 *
	 * @see Integer
	 */
	private Integer level;

	/**
	 * 体系id
	 *
	 * @see String
	 */
	private String evalSystemId;

	/**
	 * 三级指标项id,默认为空串
	 *
	 * @see String
	 */
	private String evalIndicatorId = "";

	/**
	 * 归一化类型, 百分比:1,周期:2,延时:3
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 聚合算法id
	 *
	 * @see String
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 *
	 * @see String
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 子指标id
	 *
	 * @see String
	 */
	private String childrenIds;

	public enum Level implements GenericEnum {

		FIRST(1, "一级指标"), SECOND(2, "二级指标"), THIRD(3, "三级指标");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Level(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	/**
	 * 矩阵内容
	 *
	 * @see String
	 */
	private String matrixData;
}