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
 * @date 2024/11/08
 */
@Data
@TableName("oj_practice_random_flag")
public class PracticeRandomFlag extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 附件id
	 */
	private String attachmentId;

	/**
	 * 实践id
	 */
	private String practiceId;

	/**
	 * 旗
	 */
	private String flag;

	/**
	 * 类型
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
