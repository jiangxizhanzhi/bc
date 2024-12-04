package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 练习随机标志
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_practice_random_flag")
public class PracticeRandomFlag extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 附件id
	 *
	 * @see String
	 */
	private String attachmentId;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	private String practiceId;

	/**
	 * 旗
	 *
	 * @see String
	 */
	private String flag;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	public enum FlagType {

		PRACTICE(0, "习题"), LESSON_PRACTICE(1, "课后练习");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		FlagType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
