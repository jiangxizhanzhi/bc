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
 * @date 2024/11/14
 */
@Data
@TableName("oj_practice_solved")
public class PracticeSolved extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 提交用户id
	 */
	private String submitUserId;

	/**
	 * 提交答案
	 */
	private String submitAnswer;

	/**
	 * 得分
	 */
	private Double score;

	/**
	 * 总数得分
	 */
	private Double totalScore;

	/**
	 * 解决
	 */
	private Boolean solved;

	/**
	 * 练习id
	 */
	private String practiceId;

	/**
	 * 练习类型
	 */
	private Integer practiceType;

	/**
	 * 收集
	 */
	private Boolean collection;

	/**
	 * 是否试卷
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
