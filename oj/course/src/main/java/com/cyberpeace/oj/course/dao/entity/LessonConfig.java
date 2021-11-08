package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("oj_lesson_config")
public class LessonConfig implements Serializable {

    private String resourceId;

    private String courseId;

    private String lessonId;

    private Integer index;

    private String parentId;

    private String chapterName;

    private Integer nodeType;

}
