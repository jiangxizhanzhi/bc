package com.cyberpeace.oj.crbase.controller.res;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("课程评论信息")
public class CommentRes implements Serializable {

    private String avatar;

    private String resourceId;

    private String resource;

    private String themeName;

    private String user;

    private String comment;

    private Integer commentType;

    private Date createTime;

    private String parentId;

    private Boolean isLike;

    private Integer likesCount;

    private List<CommentRes> commentReply;

}
