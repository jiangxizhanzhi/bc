package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("课程评论信息")
public class CommentRes implements Serializable {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("评论资源编号")
    private String resourceId;

    @ApiModelProperty("资源编号 课程、习题")
    private String businessId;

    @ApiModelProperty("评论主题名称")
    private String themeName;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论类型")
    private Integer businessType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String parentId;

    @ApiModelProperty("是否被点赞")
    private Boolean isLike;

    @ApiModelProperty("被点赞数量")
    private Integer likesCount;

}
