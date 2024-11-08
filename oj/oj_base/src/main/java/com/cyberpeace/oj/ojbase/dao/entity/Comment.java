package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

/**
 * 评论
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_comment")
public class Comment extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 答id
	 */
	private String replyId;

	/**
	 * 业务id
	 */
	private String businessId;

	/**
	 * 主题名称
	 */
	private String themeName;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 根id
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
