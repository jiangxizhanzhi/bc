package com.cyberpeace.oj.course.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 课程课时
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_course_lesson")
public class CourseLesson extends BaseTable {

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 是否为内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为内置")
	private Boolean builtin;

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 课时类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer type;

	/**
	 * 讲义类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("讲义类型")
	private Integer handoutType;

	/**
	 * 讲义路径或者内容
	 *
	 * @see String
	 */
	@ApiModelProperty("讲义路径或者内容")
	private String handoutUrl;

	/**
	 * 视频地址
	 *
	 * @see String
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

	/**
	 * 视频检查点
	 *
	 * @see String
	 */
	@ApiModelProperty("视频检查点")
	private String videoCheckPoint;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@TableField("`public`")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 附件编号
	 *
	 * @see String
	 */
	@ApiModelProperty("附件编号")
	private String attachmentIds;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("必修")
	private Integer compulsory;

	/**
	 * 销毁延时
	 *
	 * @see Integer
	 */
	@ApiModelProperty("销毁延时")
	private Integer destroyDelay;

	/**
	 * 课时时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时时长")
	private Integer duration;

	/**
	 * 拓扑编号
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 知识点
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 场景模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景模式")
	private Integer sceneShareMode;

	/**
	 * md讲义原文件地址
	 *
	 * @see String
	 */
	@ApiModelProperty("md讲义原文件地址")
	private String originMarkdownFile;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty("创建用户")
	private String createUserId;

	/**
	 * 试卷编号
	 *
	 * @see String
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 热度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * 课后练习公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("课后练习公开")
	private Boolean paperPublic;

	/**
	 * 路径
	 *
	 * @see String
	 */
	@TableField(exist = false,  value = "资管课时路径")
	private String path;

	public enum HandoutType implements GenericEnum {

		/** 讲义类型*/
		NEITHER(0, "都不是"), MD(1, "markdown"), PPT(2, "ppt文件"), PDF(4, "pdf"), DOC(3,
				"word文档"), XLS(5, "xls文件");

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

		public static Boolean hasFileUrl(Integer handoutType) {
			List<Integer> officeTypeList = Lists.newArrayList(HandoutType.DOC.getCode(),
					HandoutType.PPT.getCode(), HandoutType.XLS.getCode(), HandoutType.PDF.getCode());
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
