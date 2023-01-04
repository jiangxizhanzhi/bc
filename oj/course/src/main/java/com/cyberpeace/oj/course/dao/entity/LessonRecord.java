package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_lesson_record")
public class LessonRecord extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String lessonId;

	private String userId;

	private String courseId;

	private String roomId;

	private Integer learnedType;

	public enum LearnedType implements GenericEnum {

		COURSE(0, "课程"),

		CLASS_SCHEDULE(1, "课程表");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		LearnedType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
