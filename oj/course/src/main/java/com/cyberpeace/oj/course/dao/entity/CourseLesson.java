package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("oj_course_lesson")
public class CourseLesson extends BaseTable {

	/** 必修*/
	public static final int IS_REQUIRED = 2;

	/** 选修*/
	public static final int IS_ELECTIVE = 1;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Boolean builtin;

	private String courseId;

	private String name;

	private Integer type;

	private Integer handoutType;

	private String handoutUrl;

	private String videoUrl;

	private Integer difficulty;

	@TableField("public")
	private Boolean isPublic;

	private String attachmentIds;

	private Integer compulsory;

	private Integer destroyDelay;

	private Integer duration;

	private String topoId;

	private String knowledge;

	private Integer sceneShareMode;

	private String originMarkdownFile;

	private String createUserId;

	private String paperId;

	private Integer heat;

}
