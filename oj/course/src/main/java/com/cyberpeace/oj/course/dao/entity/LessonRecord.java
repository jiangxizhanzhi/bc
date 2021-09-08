package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.oj.course.service.CourseLessonService;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("course_lessonrecord")
public class LessonRecord implements Serializable {

    /** 学习中 暂未使用*/
    public static final int LEARNING = 1;

    /** 已学习*/
    public static final int FINISH = 2;

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String course;

    private String lesson;

    private String user;

    private Integer progress;

    private Date startTime;

    private Date endTime;
}
