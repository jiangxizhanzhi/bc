package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("course_lessonscene")
public class LessonScene implements Serializable {

    private Integer id;

    private String resourceId;

    private String user;

    private Integer isShared;

    private String scene;

    private Date destroyTime;

    private Date pauseTime;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer isWeb;

    private String lesson;

    private String classroomGroup;

}
