package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LessonRecordRes implements Serializable {

    /**
     * 课程id
     *
     * @See String
     */
    @ApiModelProperty("课程id")
    private String courseId;

    /**
     * 课程名称
     *
     * @See String
     */
    @ApiModelProperty("课程名称")
    private String courseName;

    /**
     * 课时id
     *
     * @See String
     */
    @ApiModelProperty("课时id")
    private String lessonId;

    /**
     * 课时目录
     *
     * @See String
     */
    @ApiModelProperty("课时目录")
    private String lessonIndex;

    /**
     * 课时名称
     *
     * @See String
     */
    @ApiModelProperty("课时名称")
    private String lessonName;

    /**
     * 课时学习时间
     *
     * @See Date
     */
    @ApiModelProperty("课时学习时间")
    private Date lessonRecordDate;

    /**
     * 标记
     *
     * @See Boolean
     */
    @ApiModelProperty("标记")
    private Boolean flag;
}

