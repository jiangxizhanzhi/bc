package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.baseinterface.Create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonAddReq implements Serializable {

	@ApiModelProperty("录制状态 0=未完成 1=完成 ")
	private Integer recordingStatus;

	@ApiModelProperty("课时名称")
	@NotBlank(groups = { Create.class })
	private String name;

	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	private Integer type;

	@ApiModelProperty("内容描述")
	private String description;

	@ApiModelProperty("新课时类型 0=文档节 1=视频节 2=单机实验节 3=网络实验节")
	@NotNull(groups = {Create.class})
	private Integer lessonType;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty(name = "attachment_ids", value = "附件编号")
	private String attachmentIds;

	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	@ApiModelProperty("课时时长")
	@NotNull(groups = { Create.class })
	private Integer duration;

	@ApiModelProperty("课时类型 0=未配置 1=选修 2=必须")
	@NotNull(groups = { Create.class })
	private Integer compulsory;

	@ApiModelProperty(name = "is_public", value = "是否公开 0=隐藏 1=公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	@ApiModelProperty(name = "course_id", value = "课程编号")
	@NotBlank(groups = { Create.class })
	private String courseId;

	@ApiModelProperty("讲义文件")
	private MultipartFile handout;

	@ApiModelProperty("视频文件")
	private MultipartFile video;

	@ApiModelProperty(name = "oj_scene_config", value = "场景配置信息")
	private String ojSceneConfig;

	@ApiModelProperty(name = "destroy_delay", value = "场景存活时间 单位小时")
	private Integer destroyDelay;

	@ApiModelProperty(name = "scene_share_mode", value = "场景隔离类型 1=共享 2=个人")
	private Integer sceneShareMode;

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
	
	public Integer getType() {
		if (null == this.lessonType) {
			return null;
		}

		return CourseLesson.judgeExperiment(lessonType)
				? CourseLesson.LessonType.EXPERIMENT.getCode()
				: CourseLesson.LessonType.THEORY.getCode();
	}

	public void setLesson_type(Integer lessonType) {
		this.lessonType = lessonType;
	}
}
