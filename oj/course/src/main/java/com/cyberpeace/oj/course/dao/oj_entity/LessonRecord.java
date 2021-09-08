package com.cyberpeace.oj.course.dao.oj_entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LessonRecord implements Serializable {

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
