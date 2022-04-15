package com.cyberpeace.oj.course.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_course_lesson")
public class CourseLesson extends BaseTable {

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

	private Boolean paperPublic;

	public enum HandoutType implements GenericEnum {

		NEITHER(0, "都不是"), MD(1, "markdown"), PPT(2, "ppt文件"), PDF(4, "pdf"), DOC(3,
				"word文档"), XLS(4, "xls文件");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		HandoutType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public static Boolean isOfficeFile(Integer handoutType) {
			List<Integer> officeTypeList = Lists.newArrayList(HandoutType.DOC.getCode(),
					HandoutType.PPT.getCode(), HandoutType.XLS.getCode());
			return officeTypeList.contains(handoutType);
		}

	}

	public enum LessonType implements GenericEnum {

		THEORY(0, "理论课"), EXPERIMENT(1, "实验课");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		LessonType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum CompulsoryType implements GenericEnum {

		NOT_CONFIGURED(0, "未配置"), ELECTIVE(1, "选修"), REQUIRED(2, "必修");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		CompulsoryType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
