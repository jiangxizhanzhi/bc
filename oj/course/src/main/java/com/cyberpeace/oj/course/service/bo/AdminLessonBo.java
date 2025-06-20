package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员课程 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonBo` 的 Java 类，用于表示管理员课程业务对象（BO, Business Object）。该类主要用于存储和管理与课程相关的详细信息。下面是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入**： 
 * - 使用了 `com.alibaba.fastjson.JSONObject` 进行 JSON 数据处理。 
 * - 使用了 `com.cyberpeace.cs.service.context.AttachmentBo` 作为附件业务对象。 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解简化属性的 getter 和 setter 方法生成。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述 API 中返回的对象字段。 
 * - **实现接口**：`Serializable`，表明这个类可以序列化。 
 * ### 属性 
 * - `resourceId`, `courseId`, `name`: 分别代表资源编号、课程编号和课时名称。 
 * - `type`, `handoutType`: 整型变量，分别代表类型和讲义类型。 
 * - `handoutUrl`, `handoutName`, `originMarkdownFile`: 字符串变量，分别代表讲义地址、讲义名称和讲义原文件地址。 
 * - `videoUrl`, `difficulty`, `isPublic`, `compulsory`, `destroyDelay`, `duration`, `topoId`, `sceneShareMode`, `videoDuration`: 分别代表视频地址、难度、是否公开、是否必修、销毁时间、课时时长、拓扑编号、场景共享类型和视频时长。 
 * - `sceneConfig`: 使用 `JSONObject` 来存储场景配置数据。 
 * - `practiceCount`, `paperId`, `knowledge`, `LessonFile`: 分别代表课后练习习题数量、试卷编号、知识点列表和课时附件数据。 
 * ### 特点 
 * - 使用了 Lombok 的注解减少了样板代码。 
 * - 使用了 Swagger 注解来为 API 文档提供详细的字段说明。 
 * - 包含了一些关于课程内容（如讲义、视频）和课程元数据（如难度、是否公开）的详细信息。 
 * - 通过引入 `AttachmentBo` 对象列表来管理课程相关的附件数据。 
 * - 支持序列化，可能用于持久化或网络传输。 
 * 这段代码简洁明了地定义了一个用于管理课程信息的数据模型，并且使用了现代的开发工具和库来简化开发工作。 
**/
@Getter
@Setter
public class AdminLessonBo implements Serializable {

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
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课时名称")
	private String name;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("类型")
	private Integer type;

    /**
     * 讲义类型
     *
     * @see Integer
     */
    @ApiModelProperty("讲义类型 0=什么都不是 1=markdown 2=ppt 4=pdf")
	private Integer handoutType;

    /**
     * 讲义 URL
     *
     * @see String
     */
    @ApiModelProperty("讲义地址")
	private String handoutUrl;

    /**
     * 讲义名称
     *
     * @see String
     */
    @ApiModelProperty("讲义名称")
	private String handoutName;

    /**
     * origin markdown 文件
     *
     * @see String
     */
    @ApiModelProperty("讲义原文件地址")
	private String originMarkdownFile;

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
    @ApiModelProperty("是否公开 true=公开 false=非公开")
	private Boolean isPublic;

    /**
     * 必修
     *
     * @see Integer
     */
    @ApiModelProperty("是否必修 0=未配置 1=选修 2=必修")
	private Integer compulsory;

    /**
     * 销毁延迟
     *
     * @see Integer
     */
    @ApiModelProperty("销毁时间")
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
     * 场景共享模式
     *
     * @see Integer
     */
    @ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

    /**
     * 场景配置
     *
     * @see JSONObject
     */
    @ApiModelProperty("场景配置数据")
	private JSONObject sceneConfig;

    /**
     * 实践计数
     *
     * @see Integer
     */
    @ApiModelProperty("课后练习习题数量")
	private Integer practiceCount;

    /**
     * 纸id
     *
     * @see String
     */
    @ApiModelProperty("试卷编号")
	private String paperId;

    /**
     * 知识
     *
     * @see List<String>
     */
    @ApiModelProperty("知识点")
	private List<String> knowledge;

    /**
     * 课程档案
     *
     * @see List<AttachmentBo>
     */
    @ApiModelProperty("课时附件数据")
	private List<AttachmentBo> LessonFile;

    @ApiModelProperty(value = "视频时长")
    private Integer videoDuration;

}

