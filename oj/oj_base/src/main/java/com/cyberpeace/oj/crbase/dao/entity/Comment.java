package com.cyberpeace.oj.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("x_comment_comment")
public class Comment implements Serializable {

    /** 评论类型-课程*/
    public static final int COURSE = 1;

    /** 评论类型-课时*/
    public static final int LESSON = 2;

    /** 评论类型-练习题*/
    public static final int EXERCISE = 3;

    private String resourceId;

    private String reply;

    private String resource;

    private String themeName;

    private String user;

    private String comment;

    private Integer commentType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String parentId;

}
