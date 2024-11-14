package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理课时列表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminLessonListBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课时类型 0=理论课 1=实验课
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	private Integer type;

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 讲义类型
	 */
	@ApiModelProperty("讲义类型")
	private Integer handoutType;

	/**
	 * 讲义地址
	 */
	@ApiModelProperty("讲义地址")
	private String handoutUrl;

	/**
	 * 视频地址
	 */
	@ApiModelProperty("视频地址")
	private String videoUrl;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 课时公开和隐藏 true 公开
	 */
	@ApiModelProperty("课时公开和隐藏 true 公开")
	private Boolean isPublic;

	/**
	 * 是否必须 0=未配置 1=选修 2=必修
	 */
	@ApiModelProperty("是否必须 0=未配置 1=选修 2=必修")
	private Integer compulsory;

	/**
	 * 课时时长
	 */
	@ApiModelProperty("课时时长")
	private Integer duration;

	/**
	 * 拓扑编号
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 创建人id
	 */
	@ApiModelProperty("创建人id")
	private String createUserId;

	/**
	 * 创建人名称
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 练习题数量
	 */
	@ApiModelProperty("练习题数量")
	private Integer practiceCount;

	/**
	 * 练习公开与隐藏
	 */
	@ApiModelProperty("练习公开与隐藏")
	private Boolean paperPublic;

	/**
	 * 是否有实验报告
	 */
	@ApiModelProperty("是否有实验报告")
	private Boolean hasReport;

	/**
	 * 课时附件数据
	 */
	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> lessonFile;

	/**
	 * 场景共享类型 1=个人 2=共享 3=小组
	 */
	@ApiModelProperty("场景共享类型 1=个人 2=共享 3=小组")
	private Integer sceneShareMode;
}
