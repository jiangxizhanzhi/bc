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

/**
 * 课程
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("oj_course")
public class Course extends AuthTable {

	/**
	 * 课程编号
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 是否内置
	 */
	@ApiModelProperty("是否内置")
	private Boolean builtin;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 分类编号
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 图片路径
	 */
	@ApiModelProperty("图片路径")
	private String logo;

	/**
	 * 大纲
	 */
	@ApiModelProperty("大纲")
	private String outline;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 创建人编号
	 */
	@ApiModelProperty("创建人编号")
	private String createUserId;

	/**
	 * 修改人编号
	 */
	@ApiModelProperty("修改人编号")
	private String modifyUserId;

	/**
	 * 是否公开
	 */
	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 热度
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * 讲师
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 学分
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 课时数量
	 */
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
