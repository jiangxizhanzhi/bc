package com.cyberpeace.oj.course.dao.oj_entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
public class Lesson extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private Integer builtin;

    private String course;

    private String name;

    private Integer type;

    private Integer handoutType;

    private String handoutFile;

    private String video;

    private Integer difficulty;

    @TableField("public")
    private Integer isPublic;

    private Integer lessonType;

    private Integer duration;

    private Integer destroyDelay;

    private String ojSceneConfig;

    private Integer shareMode;

    private String knowledge;

    private String createUser;

    private String testPaper;

    private Integer testPaperPublic;

    private Integer heat;

    private Integer status;

}
