package com.cyberpeace.oj.course.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("course_classroomgrouptemplate")
public class ClassroomGroupTemplate implements Serializable {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String groups;

    private String classroom;

    private Integer status;

}
