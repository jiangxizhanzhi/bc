package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 课堂练习上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ClassroomPracticeContext implements Serializable {

    /**
     * 课堂id
     *
     * @see String
     */
    private String classroomId;

    /**
     * 课时id
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
     * 是否犯
     *
     * @see Boolean
     */
    private Boolean isCommit=false;

    /**
     * 是否标记
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
