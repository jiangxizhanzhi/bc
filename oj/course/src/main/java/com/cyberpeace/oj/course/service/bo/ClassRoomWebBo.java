package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassRoomWebBo extends BaseClassTimeBo implements Serializable {

    /**
     * 课堂id
     *
     */
    private String roomId;

    /**
     * 课程id
     *
     * @see String
     */
    @ApiModelProperty("课程id")
    private String courseId;

    /**
     * 课程名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
    private String courseName;

    /**
     * 课时id
     *
     * @see String
     */
    @ApiModelProperty("课时id")
    private String lessonId;

    /**
     * 课时名称
     *
     * @see String
     */
    @ApiModelProperty("课时名称")
    private String lessonName;

    /**
     * 创建人
     *
     * @see String
     */
    @ApiModelProperty("创建人/教师")
    private String createUserId;

    /**
     * 创建人名称
     *
     * @see String
     */
    @ApiModelProperty("创建人名称")
    private String createUserName;

    /**
     * 是否签到
     *
     * @see Boolean
     */
    @ApiModelProperty("是否签到")
    private Boolean isSignIn;

	/**
	 * 教学课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("教学课程名称")
	private String planName;

}
