package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("course_course")
public class Course extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;
}
