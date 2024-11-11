package com.cyberpeace.oj.course.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 课程课程
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("oj_course_lesson")
public class CourseLesson extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 内置
	 */
	@ApiModelProperty("是否为内置")
	private Boolean builtin;

	/**
	 * 课程id
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("课时类型")
	private Integer type;

	/**
	 * 讲义类型
	 */
	@ApiModelProperty("讲义类型")
	private Integer handoutType;

	/**
	 * 讲义 URL
	 */
	@ApiModelProperty("讲义路径或者内容")
	private String handoutUrl;

	/**
	 * 视频 URL
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 是否公开
	 */
	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 附件id列表
	 */
	@ApiModelProperty("附件编号")
	private String attachmentIds;

	/**
	 * 必修
	 */
	@ApiModelProperty("必修")
	private Integer compulsory;

	/**
	 * 销毁延迟
	 */
	@ApiModelProperty("销毁延时")
	private Integer destroyDelay;

	/**
	 * 期间
	 */
	@ApiModelProperty("课时时长")
	private Integer duration;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 知识
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty("场景模式")
	private Integer sceneShareMode;

	/**
	 * origin markdown 文件
	 */
	@ApiModelProperty("md讲义原文件地址")
	private String originMarkdownFile;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("创建用户")
	private String createUserId;

	/**
	 * 试卷id
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 热
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * 论文 公共
	 */
	@ApiModelProperty("课后练习公开")
	private Boolean paperPublic;

	/**
	 * 路径
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
