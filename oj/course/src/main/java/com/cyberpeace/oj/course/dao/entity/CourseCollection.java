package com.cyberpeace.oj.course.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("course_coursecollection")
public class CourseCollection implements Serializable {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String user;

    private String course;

    private Boolean isCollection;

    private Date updateTime;

}
