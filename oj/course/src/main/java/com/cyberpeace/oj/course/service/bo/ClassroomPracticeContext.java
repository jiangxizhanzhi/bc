package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 课堂练习环境
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ClassroomPracticeContext implements Serializable {

    /**
     * 课堂id
     */
    private String classroomId;

    /**
     * 课id
     */
    private String lessonId;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 是提交
     */
    private Boolean isCommit=false;

    /**
     * 是标记
     */
    private Boolean isMark=false;

    /**
     * 用户名
     */
    private String username;

}
