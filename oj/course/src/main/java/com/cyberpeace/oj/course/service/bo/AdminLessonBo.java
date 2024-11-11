package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员课程 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class AdminLessonBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程id
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 名称
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private Integer type;

	/**
	 * 讲义类型
	 */
	@ApiModelProperty("讲义类型 0=什么都不是 1=markdown 2=ppt 4=pdf")
	private Integer handoutType;

	/**
	 * 讲义 URL
	 */
	@ApiModelProperty("讲义地址")
	private String handoutUrl;

	/**
	 * 讲义名称
	 */
	@ApiModelProperty("讲义名称")
	private String handoutName;

	/**
	 * origin markdown 文件
	 */
	@ApiModelProperty("讲义原文件地址")
	private String originMarkdownFile;

	/**
	 * 视频 URL
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 是公共
	 */
	@ApiModelProperty("是否公开 true=公开 false=非公开")
	private Boolean isPublic;

	/**
	 * 必修
	 */
	@ApiModelProperty("是否必修 0=未配置 1=选修 2=必修")
	private Integer compulsory;

	/**
	 * 销毁延迟
	 */
	@ApiModelProperty("销毁时间")
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
	 * 场景共享模式
	 */
	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	/**
	 * 场景配置
	 */
	@ApiModelProperty("场景配置数据")
	private JSONObject sceneConfig;

	/**
	 * 练习计数
	 */
	@ApiModelProperty("课后练习习题数量")
	private Integer practiceCount;

	/**
	 * 试卷id
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 知识
	 */
	@ApiModelProperty("知识点")
	private List<String> knowledge;

	/**
	 * 课程档案
	 */
	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> LessonFile;

}
