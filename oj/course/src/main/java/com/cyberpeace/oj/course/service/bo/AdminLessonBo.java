package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.cs.service.context.video.VideoCheckPointConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminLessonBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("课时名称")
	private String name;

	@ApiModelProperty("类型")
	private Integer type;

	@ApiModelProperty("讲义类型 0=什么都不是 1=markdown 2=ppt 4=pdf")
	private Integer handoutType;

	@ApiModelProperty("讲义地址")
	private String handoutUrl;

	@ApiModelProperty("讲义名称")
	private String handoutName;

	@ApiModelProperty("讲义原文件地址")
	private String originMarkdownFile;

	@ApiModelProperty("视频地址")
	private String videoUrl;

	@ApiModelProperty("视频长度(秒)")
	private Integer videoDuration;

	@ApiModelProperty("视频检查点分布")
	private VideoCheckPointConfig videoCheckPointConfig;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("是否公开 true=公开 false=非公开")
	private Boolean isPublic;

	@ApiModelProperty("是否必修 0=未配置 1=选修 2=必修")
	private Integer compulsory;

	@ApiModelProperty("销毁时间")
	private Integer destroyDelay;

	@ApiModelProperty("课时时长")
	private Integer duration;

	@ApiModelProperty("拓扑编号")
	private String topoId;

	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	@ApiModelProperty("场景配置数据")
	private JSONObject sceneConfig;

	@ApiModelProperty("课后练习习题数量")
	private Integer practiceCount;

	@ApiModelProperty("试卷编号")
	private String paperId;

	@ApiModelProperty("知识点")
	private List<String> knowledge;

	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> LessonFile;

}
