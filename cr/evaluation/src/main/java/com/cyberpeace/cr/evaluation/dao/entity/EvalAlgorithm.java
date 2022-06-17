package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 
 * @TableName cr_eval_algorithm
 */
@TableName(value ="cr_eval_algorithm")
@Data
public class EvalAlgorithm extends BaseTable {
    

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
     * 格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4
     */
    private Integer type;

    /**
     * 
     */
    private Integer algorithmLanguage;

    /**
     * 算法语言
        1：python
     */
    private String code;

	/**
	 * 状态
	 0：代表正常，1：代表编辑过，页面看不到
	 */
	private Integer state = 0;

    /**
     * 
     */
    private String params;

    /**
     * 
     */
    private Integer paramsNum;

    /**
     * 创建人
     */
    private String createUserId;

	public enum Type implements GenericEnum {

		SCORING_ALGORITHM(1, "评分算法"), FORMAT_ALGORITHM(2, "格式化算法"), MATCHING_ALGORITHM(3,
				"模型匹配算法"), AGGREGATING_ALGORITHM(4, "聚合算法");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}