package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("course_course")
public class Course extends BaseTable {

	/** 初级*/
	public static final int PRIMARY = 0;

	/** 中级*/
	public static final int MIDDLE = 1;

	/** 高级*/
	public static final int SENIOR = 2;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@TableField(exist = false)
	private Integer lock;

	private Integer builtin;

	private Integer auth;

	private String authOrganization;

	private Integer share;

	private String shareTeachers;

	private String name;

	private String description;

	private String direction;

	private String logo;

	private String courseWriteup;

	private Integer difficulty;

	private String createUser;

	private String lastEditUser;

	@TableField("public")
	private Integer coursePublic;

	private Integer eBook;

	private Integer status;

	private Integer heat;

	private String lecturer;

	private Integer score;

	private String courseNum;
}
