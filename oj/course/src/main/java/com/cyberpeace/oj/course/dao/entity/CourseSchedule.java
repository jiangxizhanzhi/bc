package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("course_courseschedule")
public class CourseSchedule implements Serializable {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private Date start;

    private Date end;

    private String course;

    private String lesson;

    private String organization;

    private String organizationInfo;

    private String createUser;

    private Integer status;

}
