package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 提交答案上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class SubmitAnswerContext implements Serializable {

    /**
     * 课程id
     *
     * @see String
     */
    private String courseId;

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

    /**
     * 用户id
     *
     * @see String
     */
    private String userId;

}
