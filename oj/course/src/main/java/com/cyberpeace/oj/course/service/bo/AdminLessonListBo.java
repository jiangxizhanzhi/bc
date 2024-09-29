package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminLessonListBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("课程名称")
	private String courseName;

	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	private Integer type;

	@ApiModelProperty("课时名称")
	private String name;

	@ApiModelProperty("讲义类型")
	private Integer handoutType;

	@ApiModelProperty("讲义地址")
	private String handoutUrl;

	@ApiModelProperty("视频地址")
	private String videoUrl;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("课时公开和隐藏 true 公开")
	private Boolean isPublic;

	@ApiModelProperty("是否必须 0=未配置 1=选修 2=必修")
	private Integer compulsory;

	@ApiModelProperty("课时时长")
	private Integer duration;

	@ApiModelProperty("拓扑编号")
	private String topoId;

	@ApiModelProperty("创建人id")
	private String createUserId;

	@ApiModelProperty("创建人名称")
	private String createUserName;

	@ApiModelProperty("练习题数量")
	private Integer practiceCount;

	@ApiModelProperty("练习公开与隐藏")
	private Boolean paperPublic;

	@ApiModelProperty("是否有实验报告")
	private Boolean hasReport;

	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> lessonFile;

	@ApiModelProperty("场景共享类型 1=个人 2=共享 3=小组")
	private Integer sceneShareMode;

	@ApiModelProperty("是否连续")
	private Boolean continuous;

	@ApiModelProperty("是否共享")
	private Boolean shared;

	@ApiModelProperty("指导")
	private String guidance;

	@ApiModelProperty("是否可编辑")
	private Boolean editable;
}
