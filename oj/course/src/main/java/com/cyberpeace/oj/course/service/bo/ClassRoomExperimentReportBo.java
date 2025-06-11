package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.constant.ExperimentReportEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ClassRoomExperimentReportBo implements Serializable {
    @ApiModelProperty("课程id")
    private String courseId;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课时id")
    private String lessonId;

    @ApiModelProperty("课时名称")
    private String lessonName;

    /**
     * 提交状态 0 待提交 1 待批改 2 已批改
     * @see ExperimentReportEnum
     */
    @ApiModelProperty("状态 待提交、 待批改、 已批改")
    private Integer submitStatus;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("课堂id")
    private String roomId;

    @ApiModelProperty("课堂对应的计划名称")
    private String planName;

    @ApiModelProperty("课堂对应的计划id")
    private String trainPlanId;

    @ApiModelProperty("是否通过")
    private Boolean passed;

    @ApiModelProperty("笔记id")
    private String noteId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

}
