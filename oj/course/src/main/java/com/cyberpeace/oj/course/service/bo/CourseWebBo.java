package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程WebBo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("前端课程基本信息")
public class CourseWebBo implements Serializable {

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
	private String courseNum;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 课程方向
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 课程方向名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课程海报
	 *
	 * @see String
	 */
	@ApiModelProperty("课程海报")
	private String logo;


    @ApiModelProperty("课程附件")
    private String attachment;

    @ApiModelProperty("组成课程的实验/科目/演训")
    private List<String> lessons;

    @ApiModelProperty("学生人数")
    private Integer studentNum;


    /**
	 * 课程难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程创建人
	 *
	 * @see String
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 创建人名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 学分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer ebook;

	/**
	 * 课程课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程课时时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 进度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("进度")
	private Integer process = 0;;

}
