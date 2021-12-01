package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("oj_lesson_config")
public class LessonConfig extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String courseId;

	private String lessonId;

	@TableField("`index`")
	private Integer index;

	private String parentId;

	private String name;

	private Integer nodeType;

}
