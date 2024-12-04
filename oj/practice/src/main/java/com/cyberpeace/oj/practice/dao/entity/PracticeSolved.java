package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

/**
 * 练习解决
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_practice_solved")
public class PracticeSolved extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 提交用户id
	 *
	 * @see String
	 */
	private String submitUserId;

	/**
	 * 提交答案
	 *
	 * @see String
	 */
	private String submitAnswer;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	private Double score;

	/**
	 * 总分
	 *
	 * @see Double
	 */
	private Double totalScore;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	private Boolean solved;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	private String practiceId;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	private Integer practiceType;

	/**
	 * 收集
	 *
	 * @see Boolean
	 */
	private Boolean collection;

	/**
	 * 是纸
	 *
	 * @see Boolean
	 */
	private Boolean isPaper = false;

	public enum SolvedStatus implements GenericEnum {
		TRIED(0, "尝试过"),
		SOLVED(1, "已解决"),
		NOT_START(2,"未开始");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		SolvedStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
