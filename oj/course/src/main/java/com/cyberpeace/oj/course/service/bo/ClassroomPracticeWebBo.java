package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.constant.ExperimentReportEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ClassroomPracticeWebBo implements Serializable {
    @ApiModelProperty("课程id")
    private String courseId;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课时id")
    private String lessonId;

    @ApiModelProperty("课时名称")
    private String lessonName;

    /**
     * 提交状态 1 待提交 2 待批改 3 已批改
     * @see ExperimentReportEnum
     */
    @ApiModelProperty("状态  1 待提交 2 待批改 3 已批改, type=46")
    private Integer submitStatus;

    @ApiModelProperty("课堂id")
    private String roomId;

    @ApiModelProperty("课堂对应的计划名称")
    private String planName;

    @ApiModelProperty("课堂对应的计划id")
    private String trainPlanId;

    @ApiModelProperty("笔记id")
    private String noteId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("课后练习试卷Id")
    private String paperId;

    @ApiModelProperty("课后练习试卷名称")
    private String paperName;

    @ApiModelProperty("批改人")
    private String corrector;

    /**
     * 课后练习级别
     * @see com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate.LevelType
     */
    @ApiModelProperty("课后练习级别 1-优， 2-良， 3-中， 4-差，type=47")
    private Integer level;
}
