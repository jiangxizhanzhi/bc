package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentBo implements Serializable {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("评论资源编号")
    private String resourceId;

    @ApiModelProperty("资源编号 课程、习题")
    private String resource;

    @ApiModelProperty("评论主题名称")
    private String themeName;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论类型")
    private Integer commentType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String parentId;

    @ApiModelProperty("是否被点赞")
    private Boolean isLike;

    @ApiModelProperty("被点赞数量")
    private Integer likesCount;

    @ApiModelProperty("被回复数量")
    private List<CommentBo> commentReply;

}
