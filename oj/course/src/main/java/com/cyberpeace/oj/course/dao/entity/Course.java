package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_course")
public class Course extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课程编号")
	private String resourceId;

	@ApiModelProperty("是否内置")
	private Boolean builtin;

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("图片路径")
	private String logo;

	@ApiModelProperty("大纲")
	private String outline;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("创建人编号")
	private String createUserId;

	@ApiModelProperty("修改人编号")
	private String modifyUserId;

	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("课程类型")
	private Integer courseType;

	@ApiModelProperty("热度")
	private Integer heat;

	@ApiModelProperty("讲师")
	private String lecturer;

	@ApiModelProperty("学分")
	private Integer score;

	@ApiModelProperty("课程编号")
	private String courseNum;

	private Integer role;

	@TableField(exist = false)
	@ApiModelProperty("课时数量")
	private Integer lessonCount;

	public enum Difficulty implements GenericEnum {

		PRIMARY(0, "初级"), MIDDLE(1, "中级"), HIGH(2, "高级");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Difficulty(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
