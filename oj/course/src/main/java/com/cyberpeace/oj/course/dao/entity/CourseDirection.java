package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("course_direction")
public class CourseDirection {

	private Integer id;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private Integer status;

	private Integer eBook;

	private Date updateTime;

}
