package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 评论
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_comment")
public class Comment extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 答id
	 *
	 * @see String
	 */
	private String replyId;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 主题名称
	 *
	 * @see String
	 */
	private String themeName;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 根id
	 *
	 * @see String
	 */
	private String rootId;

	public enum CommentType implements GenericEnum {

		COURSE(1, "课程"),

		LESSON(2, "课时"),

		EXERCISE(3, "实操题"),
		AFTER_CLASS_EXERCISE(4, "课后练习"),
		THEORY(5, "理论题"),
		;

		@Getter
		private Integer code;

		@Getter
		private String desc;

		CommentType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
