package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassroomPracticeContext implements Serializable {

    private String classroomId;

    private String lessonId;

    private String organizationId;

    private Boolean isCommit=false;

    private Boolean isMark=false;

    private String username;

}
