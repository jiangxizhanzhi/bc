package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.*;


/**
 * 课程配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_lesson_config")
public class LessonConfig extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	private String courseId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 指数
	 *
	 * @see Integer
	 */
	@TableField("`index`")
	private Integer index;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	private String parentId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 节点类型
	 *
	 * @see Integer
	 */
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
