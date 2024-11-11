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
 * @date 2024/11/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_lesson_config")
public class LessonConfig extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课程id
	 */
	private String courseId;

	/**
	 * 课id
	 */
	private String lessonId;

	/**
	 * 指数
	 */
	@TableField("`index`")
	private Integer index;

	/**
	 * 父母id
	 */
	private String parentId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 节点类型
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
