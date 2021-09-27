package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("course_classroomgroupinfo")
public class ClassroomGroupInfo implements Serializable {

    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String classroom;

    private Integer groupNo;

    private String groupUser;

    private Integer destroyDelay;

    private Integer shareMode;

    private String lesson;

    private String ojSceneConfig;

}
