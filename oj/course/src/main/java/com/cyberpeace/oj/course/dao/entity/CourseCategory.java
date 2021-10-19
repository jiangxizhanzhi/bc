package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("oj_course_category")
public class CourseCategory extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/** 分类名称*/
	private String name;

	/** 课程类型0=基础 2=攻击 3=防御*/
	private Integer courseType;

	/** 节点编码 格式如 父级节点code + ',' + 当前节点编号*/
	private String code;

}
