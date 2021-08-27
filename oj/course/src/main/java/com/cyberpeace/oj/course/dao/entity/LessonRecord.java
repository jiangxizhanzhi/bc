package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("course_lessonrecord")
public class LessonRecord implements Serializable {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String lesson;

    private String user;

    private Integer progress;

    private Date startTime;

    private Date endTime;
}
