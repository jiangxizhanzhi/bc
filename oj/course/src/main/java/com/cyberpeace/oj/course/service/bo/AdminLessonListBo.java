package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员课程列表 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonListBo` 的 Java 类，该类用于表示管理员课程列表的数据传输对象（BO, Business Object）。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **导入**: 导入了 `io.swagger.annotations` 包中的注解用于文档生成，以及 `lombok` 包中的注解用于简化代码。 
 * - **继承**: 实现了 `Serializable` 接口，表示该类的对象可以被序列化。 
 * ### 注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 为所有字段生成 getter 方法。 
 * - `@Setter`: 为所有字段生成 setter 方法。 
 * - **Swagger 注解**: 
 * - `@ApiModel`: 用于描述该 BO 的用途。 
 * - `@ApiModelProperty`: 为每个字段提供描述信息，便于 Swagger 文档生成。 
 * ### 字段 
 * - **基本信息**: 
 * - `resourceId`: 资源编号。 
 * - `courseId`: 课程编号。 
 * - `courseName`: 课程名称。 
 * - `type`: 课时类型（理论课或实验课）。 
 * - `name`: 课时名称。 
 * - **详细信息**: 
 * - `handoutType`: 讲义类型。 
 * - `handoutUrl`: 讲义 URL。 
 * - `videoUrl`: 视频 URL。 
 * - `videoDuration`: 视频时长。 
 * - `difficulty`: 难度等级。 
 * - `isPublic`: 是否公开。 
 * - `compulsory`: 是否必修。 
 * - `duration`: 课时时长。 
 * - `topoId`: 拓扑编号。 
 * - `createUserId`: 创建用户 ID。 
 * - `createUserName`: 创建用户名称。 
 * - `practiceCount`: 练习题数量。 
 * - `paperPublic`: 练习公开与隐藏。 
 * - `hasReport`: 是否有实验报告。 
 * - `lessonFile`: 课时附件数据。 
 * - `sceneShareMode`: 场景共享类型。 
 * - `builtin`: 是否为内置课时。 
 * ### 总结 
 * - **功能**: `AdminLessonListBo` 类主要用于存储和传递管理员课程列表的相关信息。 
 * - **特性**: 使用 Lombok 注解减少了样板代码，提高了可读性和维护性。同时，通过 Swagger 注解增强了 API 文档的自动生成能力。 
 * - **适用场景**: 适用于需要管理和展示课程信息的后台系统中，尤其是需要对课程进行分类、排序和过滤的场景。 
**/
@Getter
@Setter
@ApiModel
public class AdminLessonListBo implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	private Integer type;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String name;

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
	@ApiModelProperty("讲义地址")
	private String handoutUrl;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

	/**
	 * 视频时长
	 *
	 * @see String
	 */
	@ApiModelProperty("视频时长")
	private Integer videoDuration;

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
	@ApiModelProperty("课时公开和隐藏 true 公开")
	private Boolean isPublic;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否必须 0=未配置 1=选修 2=必修")
	private Integer compulsory;

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
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人id")
	private String createUserId;

	/**
	 * 创造用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("练习题数量")
	private Integer practiceCount;

	/**
	 * 论文 公共
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("练习公开与隐藏")
	private Boolean paperPublic;

	/**
	 * 有举报
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有实验报告")
	private Boolean hasReport;

	/**
	 * 课程档案
	 *
	 * @see List<AttachmentBo>
	 */
	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> lessonFile;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景共享类型 1=个人 2=共享 3=小组")
	private Integer sceneShareMode;

	@ApiModelProperty("是否内置")
	private Boolean builtin;
}

