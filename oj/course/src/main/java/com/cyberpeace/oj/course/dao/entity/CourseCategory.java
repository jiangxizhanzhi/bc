package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 课程类别
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_course_category")
public class CourseCategory extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 课程类型
	 */
	private Integer courseType;

	/**
	 * 代号
	 */
	private String code;

	/**
	 * 父母id
	 */
	private String parentId;

	public enum CourseType implements GenericEnum {

		BASE(0, "基础"), ATTACK(2, "攻击"), DEFENSE(3, "防御");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private CourseType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
