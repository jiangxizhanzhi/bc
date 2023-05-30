package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_comment")
public class Comment extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String replyId;

	private String businessId;

	private String themeName;

	private String userId;

	private String content;

	private Integer businessType;

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
