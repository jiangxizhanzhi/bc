package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("course_lessoncollection")
public class LessonCollection {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String user;

    private String lesson;

    private Boolean isCollection;

    private Date updateTime;

}
