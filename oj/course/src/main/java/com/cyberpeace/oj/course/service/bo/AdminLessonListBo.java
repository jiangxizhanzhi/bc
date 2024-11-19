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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class AdminLessonListBo implements Serializable {

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
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课时类型 0=理论课 1=实验课
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	private Integer type;

	/**
	 * 课时名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 讲义类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("讲义类型")
	private Integer handoutType;

	/**
	 * 讲义地址
	 *
	 * @see String
	 */
	@ApiModelProperty("讲义地址")
	private String handoutUrl;

	/**
	 * 视频地址
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
	 * 课时公开和隐藏 true 公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("课时公开和隐藏 true 公开")
	private Boolean isPublic;

	/**
	 * 是否必须 0=未配置 1=选修 2=必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否必须 0=未配置 1=选修 2=必修")
	private Integer compulsory;

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
	 * 创建人id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人id")
	private String createUserId;

	/**
	 * 创建人名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 练习题数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("练习题数量")
	private Integer practiceCount;

	/**
	 * 练习公开与隐藏
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("练习公开与隐藏")
	private Boolean paperPublic;

	/**
	 * 是否有实验报告
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有实验报告")
	private Boolean hasReport;

	/**
	 * 课时附件数据
	 *
	 * @see List<AttachmentBo>
	 */
	@ApiModelProperty("课时附件数据")
	private List<AttachmentBo> lessonFile;

	/**
	 * 场景共享类型 1=个人 2=共享 3=小组
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景共享类型 1=个人 2=共享 3=小组")
	private Integer sceneShareMode;
}
