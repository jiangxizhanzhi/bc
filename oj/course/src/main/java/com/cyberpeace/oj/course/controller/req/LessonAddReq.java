/**
 * 这段代码定义了一个名为 `LessonAddReq` 的类，用于表示创建新课程时的请求体。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`LessonAddReq` 
 * - **实现接口**：`Serializable` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModel`：用于 Swagger 文档生成，描述整个模型。 
 * - `@ApiModelProperty`：用于 Swagger 文档生成，描述每个字段。 
 * ### 字段 
 * - `name`：课时名称（必填） 
 * - `type`：课时类型（必填），取值为 0 或 1 
 * - `knowledge`：知识点（非必填） 
 * - `attachmentIds`：附件 ID 列表（非必填） 
 * - `difficulty`：难度（必填），取值为 0、1 或 2 
 * - `duration`：课时时长（必填） 
 * - `compulsory`：是否必修（必填），取值为 0、1 或 2 
 * - `isPublic`：是否公开（必填），取值为 true 或 false 
 * - `courseId`：课程 ID（必填） 
 * - `handout`：讲义文件（非必填） 
 * - `video`：视频文件（非必填） 
 * - `ojSceneConfig`：OJ 场景配置（非必填） 
 * - `destroyDelay`：销毁延迟（非必填），单位为小时 
 * - `sceneShareMode`：场景共享模式（非必填），取值为 1 或 2 
 * - `practiceList`：执业名单（习题列表）（非必填） 
 * - `videoDuration`：视频时长（非必填） 
 * ### 方法 
 * - 自动生成的 getter 和 setter 方法 
 * - 手动定义的 setter 方法： 
 * - `setAttachment_ids` 
 * - `setIs_public` 
 * - `setCourse_id` 
 * - `setOj_scene_config` 
 * - `setDestroy_delay` 
 * - `setScene_share_mode` 
 * - `setPractice_list` 
 * ### 总结 
 * - 该类主要用于创建新课程时的数据传输对象（DTO）。 
 * - 字段大多为必填项，确保在创建课程时所有必要信息都被提供。 
 * - 使用了 Lombok 注解简化代码，减少样板代码。 
 * - 通过 Swagger 注解支持 API 文档生成，便于开发者理解和使用。 
**/
package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 课新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LessonAddReq implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	@NotBlank(groups = { Create.class })
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	@NotNull(groups = { Create.class })
	private Integer type;

	/**
	 * 知识
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 附件id列表
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "attachment_ids", value = "附件编号")
	private String attachmentIds;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	/**
	 * 期间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时时长")
	@NotNull(groups = { Create.class })
	private Integer duration;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型 0=未配置 1=选修 2=必须")
	@NotNull(groups = { Create.class })
	private Integer compulsory;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(name = "is_public", value = "是否公开 0=隐藏 1=公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "course_id", value = "课程编号")
	@NotBlank(groups = { Create.class })
	private String courseId;

	/**
	 * 讲义
	 *
	 * @see String
	 */
	@ApiModelProperty("讲义文件")
	private String handout;

	/**
	 * 视频
	 *
	 * @see String
	 */
	@ApiModelProperty("视频文件")
	private String video;

	/**
	 * OJ场景配置
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "oj_scene_config", value = "场景配置信息")
	private String ojSceneConfig;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "destroy_delay", value = "场景存活时间 单位小时")
	private Integer destroyDelay;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "scene_share_mode", value = "场景隔离类型 1=共享 2=个人")
	private Integer sceneShareMode;

	/**
	 * 执业名单
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "practice_list", value = "习题列表")
	private String practiceList;

	@ApiModelProperty(value = "视频时长")
	private Integer videoDuration;

	public void setAttachment_ids(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	public void setIs_public(Boolean aPublic) {
		isPublic = aPublic;
	}

	public void setCourse_id(String courseId) {
		this.courseId = courseId;
	}

	public void setOj_scene_config(String ojSceneConfig) {
		this.ojSceneConfig = ojSceneConfig;
	}

	public void setDestroy_delay(Integer destroyDelay) {
		this.destroyDelay = destroyDelay;
	}

	public void setScene_share_mode(Integer sceneShareMode) {
		this.sceneShareMode = sceneShareMode;
	}

	public void setPractice_list(String practiceList) {
		this.practiceList = practiceList;
	}
}
