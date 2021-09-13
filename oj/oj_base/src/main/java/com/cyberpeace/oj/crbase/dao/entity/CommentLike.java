package com.cyberpeace.oj.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("x_comment_commentlike")
public class CommentLike implements Serializable {

    private Integer id;

    private String resourceId;

    private String user;

    private String comment;

    private Integer isLike;

    private Date createTime;

    private Date updateTime;

}
