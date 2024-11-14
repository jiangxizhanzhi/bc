package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 提交答案上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SubmitAnswerContext implements Serializable {

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 课时id
     */
    private String lessonId;

    /**
     * 用户id
     */
    private String userId;

}
