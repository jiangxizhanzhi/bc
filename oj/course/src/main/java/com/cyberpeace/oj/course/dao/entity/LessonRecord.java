package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 课程记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_lesson_record")
public class LessonRecord extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	private String courseId;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 学习类型
	 *
	 * @see Integer
	 */
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
