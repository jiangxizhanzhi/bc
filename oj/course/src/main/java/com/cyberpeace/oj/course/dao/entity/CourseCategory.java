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
@TableName("oj_course_category")
public class CourseCategory extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private Integer courseType;

	private String code;

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
