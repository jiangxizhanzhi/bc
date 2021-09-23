package com.cyberpeace.oj.ojbase.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("x_note_note")
public class Note implements Serializable {

    // 笔记类型
    /** 我的笔记*/
    public static final int NOTE = 1;
    /** 实验报告*/
    public static final int REPORT = 2;

    // 资源类型
    /** 课程*/
    public static final int COURSE = 1;
    /** 课时*/
    public static final int LESSON = 2;
    /** 练习(漏洞习题、夺旗习题)*/
    public static final int PRACTICE = 3;
    /** 课后练习*/
    public static final int LESSON_PAPER = 4;

    private Integer id;

    private String resourceId;

    private Integer auth;

    private String authOrganization;

    private Integer noteType;

    private Integer resourceType;

    private String content;

    private String user;

    private String resource;

    private Date submitTime;

    @TableField("public")
    private Boolean isPublic;

    private Date createTime;

    private Date updateTime;

    private Double score;

    private Boolean isPass;

    private String markComment;

    private String teacher;

    private Date markTime;

    private Integer status;

}
