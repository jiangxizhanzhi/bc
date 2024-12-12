package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程 Web bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("前端课程基本信息")
public class CourseWebBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
	private String resourceId;

    /**
     * 课程数量
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
	private String courseNum;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
	private String name;

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty("课程方向")
	private String categoryId;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("课程方向名称")
	private String directionName;

    /**
     * 图标
     *
     * @see String
     */
    @ApiModelProperty("课程海报")
	private String logo;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("课程难度")
	private Integer difficulty;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("课程创建人")
	private String createUserId;

    /**
     * 创造用户名称
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
     * 得分
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
     * 电子书
     *
     * @see Integer
     */
    @ApiModelProperty("课程类型")
	private Integer ebook;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课程课时数量")
	private Integer lessonCount;

    /**
     * 课程时间
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
     * 岗位
     *
     * @see Integer
     */
    @ApiModelProperty("岗位")
	private Integer role;

    @ApiModelProperty(value = "视频课数量")
    private Integer videoCount;

    @ApiModelProperty(value = "视频长度")
    private Integer videoDuration;

}
