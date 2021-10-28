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
