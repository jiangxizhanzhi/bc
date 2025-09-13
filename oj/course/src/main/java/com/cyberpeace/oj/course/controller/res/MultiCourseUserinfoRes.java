package com.cyberpeace.oj.course.controller.res;

import lombok.Data;

@Data
public class MultiCourseUserinfoRes {
    /**
     * 用户id，不要使用自增数字编号（必须）
     */
    private String userId;

    /**
     * 姓名（文字显示）
     */
    private String name;

    /**
     * 用户名（对前端显示，登录使用）
     */
    private String userName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    private Integer courseNums;

    private Integer finishedCourseNums;

    private Integer lessonNums;

    private Integer finishedLessonNums;

    private Integer taskNums;

    private Integer finishedTaskNums;

    private TrainGradesRes scoreStatistics;

}
