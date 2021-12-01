package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@TableName("oj_lesson_config")
public class LessonConfig extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String courseId;

	private String lessonId;

	@TableField("`index`")
	private Integer index;

	private String parentId;

	private String name;

	private Integer nodeType;

	public enum NodeType implements GenericEnum {

		NODE_COURSE(0, "课程"),

		NODE_CHAPTER(1, "章节"),

		NODE_LESSON(2, "课时");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NodeType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
