package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLessonConfigContext implements Serializable {

    private Integer nodeType;

    private String courseId;

    private String lessonId;

}
