package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("course_lessonconfig")
public class LessonConfig implements Serializable {

    public static final int NODE_COURSE = 0;

    public static final int NODE_CHAPTER = 1;

    public static final int NODE_LESSON = 2;

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
