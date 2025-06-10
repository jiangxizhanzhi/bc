package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    @ApiModelProperty("课时类型 0 理论， 1 实验")
    private Integer lessonType;

    @ApiModelProperty("课后练习等级， 1- 优、2 -良、 3-中、4-差")
    private Integer level;

    @ApiModelProperty("课后练习状态 0- 未提交， 1 - 已提交")
    private Boolean levelStatus;

    @ApiModelProperty("课后练习id，用于判断是存在课后练习")
    private String paperId;

    @ApiModelProperty("实验报告分数")
    private Integer exerciseScore;

    @ApiModelProperty("实验报告状态 0- 未提交， 1-  已提交")
    private Boolean exerciseStatus;

    @ApiModelProperty("实验报告通过状态 0- 未通过， 1-  已通过")
    private Boolean exercisePassedStatus;
}
