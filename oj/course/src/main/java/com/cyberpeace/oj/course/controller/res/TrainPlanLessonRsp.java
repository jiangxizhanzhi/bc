package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanLessonRsp implements Serializable {

    @ApiModelProperty("课程编号")
    private String courseId;

    @ApiModelProperty("课时id")
    private String lessonId;

    @ApiModelProperty("课时名称")
    private String lessonName;

    @ApiModelProperty("课时开始时间")
    private Date startTime;

    @ApiModelProperty("课时结束时间")
    private Date endTime;

    /**
     * 课时类型 0 理论， 1 实验
     * @see com.cyberpeace.oj.course.dao.entity.CourseLesson.LessonType
     */
    @ApiModelProperty("课时类型 0 理论， 1 实验,type=16")
    private Integer lessonType;

    /**
     * 课后练习等级， 1- 优、2 -良、 3-中、4-差
     * @see com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate.LevelType
     */
    @ApiModelProperty("课后练习等级， 1- 优、2 -良、 3-中、4-差,type=47")
    private Integer level;

    /**
     * 课后练习状态 1- 待提交， 2- 已提交 ， 3-已批改, 无状态表示没有课后练习或者未下发
     * @see com.cyberpeace.oj.ojbase.constant.ExperimentReportEnum
     */
    @ApiModelProperty("课后练习状态 1- 待提交， 2- 已提交 ， 3-已批改,type=46")
    private Integer levelStatus;

    @ApiModelProperty("实验报告分数")
    private Integer reportScore;

    /**
     * 实验报告状态 1- 待提交， 2- 已提交 ， 3-已批改, 无状态表示没有课后练习或者未下发
     * @see com.cyberpeace.oj.ojbase.constant.ExperimentReportEnum
     */
    @ApiModelProperty("实验报告状态 1- 待提交， 2- 已提交 ， 3-已批改,type=46")
    private Integer reportStatus;

    @ApiModelProperty("实验报告通过状态 0- 未通过， 1-  已通过")
    private Boolean reportPassedStatus;

	@ApiModelProperty("课堂id")
	private String classroomId;
}
