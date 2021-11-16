package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class SubmitAnswerContext implements Serializable {

    private String courseId;

    private String lessonId;

    private String userId;

}
