package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName rtks_course_name
 */
@TableName(value = "rtks_course_name")
@Data
public class CourseName implements Serializable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 知识点id
	 */
	private String name;

}