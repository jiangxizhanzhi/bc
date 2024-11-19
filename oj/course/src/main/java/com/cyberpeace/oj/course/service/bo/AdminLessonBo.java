package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.cs.service.context.video.VideoCheckPointConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理课时Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AdminLessonBo implements Serializable {

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课时名称
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
	 * 讲义类型 0=什么都不是 1=markdown 2=ppt 4=pdf
	 *
	 * @see Integer
	 */
	@ApiModelProperty("讲义类型 0=什么都不是 1=markdown 2=ppt 4=pdf")
	private Integer handoutType;

	/**
	 * 讲义地址
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
	 * 讲义原文件地址
	 *
	 * @see String
	 */
	@ApiModelProperty("讲义原文件地址")
	private String originMarkdownFile;

	/**
	 * 视频地址
	 *
	 * @see String
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

	/**
	 * 视频长度(秒)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("视频长度(秒)")
	private Integer videoDuration;

	/**
	 * 视频检查点分布
	 *
	 * @see VideoCheckPointConfig
	 */
	@ApiModelProperty("视频检查点分布")
	private VideoCheckPointConfig videoCheckPointConfig;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 是否公开 true=公开 false=非公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开 true=公开 false=非公开")
	private Boolean isPublic;

	/**
	 * 是否必修 0=未配置 1=选修 2=必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否必修 0=未配置 1=选修 2=必修")
	private Integer compulsory;

	/**
	 * 销毁时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("销毁时间")
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
	 * 场景共享类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	/**
	 * 场景配置数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景配置数据")
	private JSONObject sceneConfig;

	/**
	 * 课后练习习题数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课后练习习题数量")
	private Integer practiceCount;

	/**
	 * 试卷编号
	 *
	 * @see String
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 知识点
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("知识点")
	private List<String> knowledge;

	/**
	 * 课时附件数据
	 *
	 * @see List<AttachmentBo>
	 */
	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> LessonFile;

}
