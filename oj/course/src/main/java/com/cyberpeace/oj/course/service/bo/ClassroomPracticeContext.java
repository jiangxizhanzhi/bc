package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 课堂练习上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ClassroomPracticeContext implements Serializable {

    /**
     * 课堂id
     */
    private String classroomId;

    /**
     * 课时id
     */
    private String lessonId;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 是否犯
     */
    private Boolean isCommit=false;

    /**
     * 是否标记
     */
    private Boolean isMark=false;

    /**
     * 用户名
     */
    private String username;

}
