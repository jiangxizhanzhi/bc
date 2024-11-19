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
 * @version 1.0.0
 * @date 2024/11/14
 * @see AuthTable
 * @since 1.0.0
 */
@Data
@TableName("oj_course")
public class Course extends AuthTable {

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 是否内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否内置")
	private Boolean builtin;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 分类编号
	 *
	 * @see String
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 图片路径
	 *
	 * @see String
	 */
	@ApiModelProperty("图片路径")
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("大纲")
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 创建人编号
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人编号")
	private String createUserId;

	/**
	 * 修改人编号
	 *
	 * @see String
	 */
	@ApiModelProperty("修改人编号")
	private String modifyUserId;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 热度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 学分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 课时数量
	 *
	 * @see Integer
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
