package com.cyberpeace.oj.course.dao.oj_entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("course_direction")
public class Direction {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String code;

    private String parent;

    private String name;

    private Integer status;

    private Integer eBook;

    private Date updateTime;

}
