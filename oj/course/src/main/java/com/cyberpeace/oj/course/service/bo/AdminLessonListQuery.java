package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

/**
 * admin 课程列表查询
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class AdminLessonListQuery extends PatternBase {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 类别id
     */
    private String categoryId;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 除了课程
     */
    private String exceptCourse;

}
