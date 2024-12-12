package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.PatternBase;

import lombok.Getter;
import lombok.Setter;

/**
 * admin 课程列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
public class AdminLessonListQuery extends PatternBase {

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 类别id
     *
     * @see String
     */
    private String categoryId;

    /**
     * 课程id
     *
     * @see String
     */
    private String courseId;

    /**
     * 除了课程
     *
     * @see String
     */
    private String exceptCourse;

}
