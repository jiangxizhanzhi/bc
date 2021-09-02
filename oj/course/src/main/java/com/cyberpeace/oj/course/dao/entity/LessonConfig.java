package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("course_lessonconfig")
public class LessonConfig implements Serializable {

    private String resourceId;

    private String course;

    private String lesson;

    private Integer index;

    private String parent;

    private String chapterName;

    private Integer nodeType;

    private Integer lessonPublic;

    private Integer status;

}
