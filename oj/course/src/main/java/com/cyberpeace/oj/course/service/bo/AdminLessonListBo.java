package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员课程列表 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class AdminLessonListBo implements Serializable {

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
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 类型
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	private Integer type;

	/**
	 * 名称
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 讲义类型
	 */
	@ApiModelProperty("讲义类型")
	private Integer handoutType;

	/**
	 * 讲义 URL
	 */
	@ApiModelProperty("讲义地址")
	private String handoutUrl;

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
	@ApiModelProperty("课时公开和隐藏 true 公开")
	private Boolean isPublic;

	/**
	 * 必修
	 */
	@ApiModelProperty("是否必须 0=未配置 1=选修 2=必修")
	private Integer compulsory;

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
	 * 创造用户id
	 */
	@ApiModelProperty("创建人id")
	private String createUserId;

	/**
	 * 创造用户名称
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 练习计数
	 */
	@ApiModelProperty("练习题数量")
	private Integer practiceCount;

	/**
	 * 论文 公共
	 */
	@ApiModelProperty("练习公开与隐藏")
	private Boolean paperPublic;

	/**
	 * 有举报
	 */
	@ApiModelProperty("是否有实验报告")
	private Boolean hasReport;

	/**
	 * 课程档案
	 */
	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> lessonFile;

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty("场景共享类型 1=个人 2=共享 3=小组")
	private Integer sceneShareMode;
}
