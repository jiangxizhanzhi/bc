package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("course_lesson")
public class Lesson extends BaseTable {

	/** 理论课*/
	public static final int TYPE_THEORY = 0;

	/** 实验课*/
	public static final int TYPE_EXPERIMENT = 1;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer builtin;

	private String course;

	private String name;

	private Integer type;

	private String pdf;

	private String html;

	private Integer htmlType;

	private String video;

	private Integer difficulty;

	@TableField("public")
	private Integer lessonPublic;

	private String attachments;

	private Integer lessonType;

	private Integer duration;

	private Integer destroyDelay;

	private String ojSceneConfig;

	private Integer shareMode;

	private String knowledge;

	private String markdown;

	private String markdownFile;

	private String createUser;

	private String testPaper;

	private Integer testPaperPublic;

	private Integer heat;

	private Integer status;
}
