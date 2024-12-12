package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 
 * @TableName cr_eval_index
 */
@TableName(value = "cr_eval_index")
@Getter
@Setter
public class EvalIndex extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 指标级别：1、2、3
	 */
	private Integer level;

	/**
	 * 体系id
	 */
	private String evalSystemId;

	/**
	 * 三级指标项id,默认为空串
	 */
	private String evalIndicatorId = "";

	/**
	 * 归一化类型, 百分比:1,周期:2,延时:3
	 */
	private Integer type;

	/**
	 * 聚合算法id
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 子指标id
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
	 */
	private String matrixData;
}