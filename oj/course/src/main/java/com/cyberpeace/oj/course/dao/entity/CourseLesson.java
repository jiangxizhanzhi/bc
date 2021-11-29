package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

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

	public enum HandoutType implements GenericEnum {

		NEITHER(0, "都不是"), MD(1, "markdown"), PPT(2, "ppt文件"), PDF(4, "pdf文件");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		HandoutType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum LessonType implements GenericEnum {

		THEORY(0, "理论课"), EXPERIMENT(1, "实验课");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private LessonType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
