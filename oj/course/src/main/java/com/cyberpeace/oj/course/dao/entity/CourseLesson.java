package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

/**
 * 课程课程
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseLesson` 的实体类，用于表示一个在线课程中的课时。以下是该代码的详细分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.oj.course.dao.entity` 
 * - **导入的库**： 
 * - `com.baomidou.mybatisplus.annotation`：用于MyBatis Plus框架的注解。 
 * - `com.cyberpeace.common.baseinterface.GenericEnum`：自定义的枚举接口。 
 * - `com.cyberpeace.common.model.BaseTable`：基类表，可能包含一些通用字段如创建时间、更新时间等。 
 * - `com.google.common.collect.Lists`：Google Guava库中用于创建不可变列表的工具类。 
 * - `io.swagger.annotations.ApiModelProperty`：用于Swagger文档生成的注解。 
 * - `lombok`：简化Java代码的库，提供`@Getter`和`@Setter`注解。 
 * ### 类定义 
 * - **类名**：`CourseLesson` 
 * - **继承**：`BaseTable` 
 * - **注解**： 
 * - `@TableName("oj_course_lesson")`：指定数据库表名。 
 * - `@Getter` 和 `@Setter`：Lombok注解，自动生成getter和setter方法。 
 * ### 属性 
 * - **资源ID (`resource_id`)**：唯一标识符，使用UUID生成。 
 * - **是否为内置 (`builtin`)**：布尔值，表示是否为内置课程。 
 * - **课程ID (`courseId`)**：关联的课程编号。 
 * - **名称 (`name`)**：课时的名称。 
 * - **类型 (`type`)**：课时的类型（理论课或实验课）。 
 * - **讲义类型 (`handoutType`)**：讲义的格式类型。 
 * - **讲义URL (`handoutUrl`)**：讲义文件的URL或内容。 
 * - **视频URL (`videoUrl`)**：视频文件的URL。 
 * - **难度 (`difficulty`)**：课时的难度等级。 
 * - **是否公开 (`isPublic`)**：布尔值，表示课时是否公开。 
 * - **附件ID列表 (`attachmentIds`)**：与课时相关的附件ID列表。 
 * - **必修 (`compulsory`)**：课时的必修状态。 
 * - **销毁延迟 (`destroyDelay`)**：销毁延时时间。 
 * - **课时时长 (`duration`)**：课时的时长。 
 * - **拓扑ID (`topoId`)**：拓扑编号。 
 * - **知识点 (`knowledge`)**：课时涉及的知识点。 
 * - **场景共享模式 (`sceneShareMode`)**：场景模式。 
 * - **原始Markdown文件 (`originMarkdownFile`)**：Markdown格式的原始讲义文件地址。 
 * - **创建用户ID (`createUserId`)**：创建该课时的用户ID。 
 * - **试卷编号 (`paperId`)**：与课时相关的试卷编号。 
 * - **热度 (`heat`)**：课时的热度。 
 * - **课后练习公开 (`paperPublic`)**：课后练习是否公开。 
 * - **路径 (`path`)**：课时的路径（仅在Swagger文档中存在）。 
 * - **视频时长 (`videoDuration`)**：视频的时长。 
 * ### 枚举 
 * - **讲义类型 (`HandoutType`)**：定义了讲义的不同类型，包括Markdown、PPT、PDF、Word文档和XLS文件。 
 * - **课时类型 (`LessonType`)**：定义了课时的类型，包括理论课和实验课。 
 * - **必修类型 (`CompulsoryType`)**：定义了课时的必修状态，包括未配置、选修和必修。 
 * ### 总结 
 * 这个类是一个典型的ORM（对象关系映射）实体类，用于表示在线课程中的课时信息。它包含了各种属性来描述课时的各个方面，并且通过枚举类型来限定某些属性的取值范围。此外，该类还使用了Lombok库来简化代码，减少了样板代码的编写。 
**/
@Getter
@Setter
@TableName("oj_course_lesson")
public class CourseLesson extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为内置")
	private Boolean builtin;

	/**
	 * 课程id
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
	 * 类型
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
	 * 讲义 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("讲义路径或者内容")
	private String handoutUrl;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

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
	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 附件id列表
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
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	@ApiModelProperty("销毁延时")
	private Integer destroyDelay;

	/**
	 * 期间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时时长")
	private Integer duration;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 知识
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景模式")
	private Integer sceneShareMode;

	/**
	 * origin markdown 文件
	 *
	 * @see String
	 */
	@ApiModelProperty("md讲义原文件地址")
	private String originMarkdownFile;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建用户")
	private String createUserId;

	/**
	 * 纸id
	 *
	 * @see String
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * 论文 公共
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

	private Integer videoDuration;

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

