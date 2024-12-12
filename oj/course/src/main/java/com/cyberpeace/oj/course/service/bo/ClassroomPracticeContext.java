package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 课堂练习环境
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassroomPracticeContext implements Serializable {

    /**
     * 课堂id
     *
     * @see String
     */
    private String classroomId;

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

    /**
     * 组织id
     *
     * @see String
     */
    private String organizationId;

    /**
     * 是提交
     *
     * @see Boolean
     */
    private Boolean isCommit=false;

    /**
     * 是标记
     *
     * @see Boolean
     */
    private Boolean isMark=false;

    /**
     * 用户名
     *
     * @see String
     */
    private String username;

}
