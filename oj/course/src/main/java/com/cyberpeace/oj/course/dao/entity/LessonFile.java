package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("course_lessonfile")
public class LessonFile implements Serializable {

    private Integer id;

    private String resourceId;

    private String name;

    private String attachment;

    private String isPublic;

    @JsonIgnore
    private Integer status;

}
