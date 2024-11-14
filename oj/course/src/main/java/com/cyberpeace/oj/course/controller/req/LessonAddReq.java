package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class LessonAddReq implements Serializable {

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
	@NotBlank(groups = { Create.class })
	private String name;

	/**
	 * 课时类型 0=理论课 1=实验课
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	@NotNull(groups = { Create.class })
	private Integer type;

	/**
	 * 知识点
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 附件编号
	 */
	@ApiModelProperty(name = "attachment_ids", value = "附件编号")
	private String attachmentIds;

	/**
	 * 难度 0=初级 1=中级 2=高级
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	/**
	 * 课时时长
	 */
	@ApiModelProperty("课时时长")
	@NotNull(groups = { Create.class })
	private Integer duration;

	/**
	 * 课时类型 0=未配置 1=选修 2=必须
	 */
	@ApiModelProperty("课时类型 0=未配置 1=选修 2=必须")
	@NotNull(groups = { Create.class })
	private Integer compulsory;

	/**
	 * 是否公开 0=隐藏 1=公开
	 */
	@ApiModelProperty(name = "is_public", value = "是否公开 0=隐藏 1=公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	/**
	 * 课程编号
	 */
	@ApiModelProperty(name = "course_id", value = "课程编号")
	@NotBlank(groups = { Create.class })
	private String courseId;

	/**
	 * 讲义文件
	 */
	@ApiModelProperty("讲义文件")
	private String handout;

	/**
	 * 视频文件
	 */
	@ApiModelProperty("视频文件")
	private String video;

	/**
	 * 视频检查点分布
	 */
	@ApiModelProperty(name = "video_check_point", value = "视频检查点分布")
	private String videoCheckPoint;

	/**
	 * 场景配置信息
	 */
	@ApiModelProperty(name = "oj_scene_config", value = "场景配置信息")
	private String ojSceneConfig;

	/**
	 * 场景存活时间 单位小时
	 */
	@ApiModelProperty(name = "destroy_delay", value = "场景存活时间 单位小时")
	private Integer destroyDelay;

	/**
	 * 场景隔离类型 1=共享 2=个人
	 */
	@ApiModelProperty(name = "scene_share_mode", value = "场景隔离类型 1=共享 2=个人")
	private Integer sceneShareMode;

	/**
	 * 习题列表
	 */
	@ApiModelProperty(name = "practice_list", value = "习题列表")
	private String practiceList;

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

	public void setVideo_check_point(String videoCheckPoint) {
		this.videoCheckPoint = videoCheckPoint;
	}
}
