package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("课程评论信息")
public class CommentRes implements Serializable {

    /**
     * 化身
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 资源id
     */
    @ApiModelProperty("评论资源编号")
    private String resourceId;

    /**
     * 业务id
     */
    @ApiModelProperty("资源编号 课程、习题")
    private String businessId;

    /**
     * 主题名称
     */
    @ApiModelProperty("评论主题名称")
    private String themeName;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 评论
     */
    @ApiModelProperty("评论内容")
    private String comment;

    /**
     * 业务类型
     */
    @ApiModelProperty("评论类型")
    private Integer businessType;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 父母id
     */
    private String parentId;

    /**
     * 就像
     */
    @ApiModelProperty("是否被点赞")
    private Boolean isLike;

    /**
     * 喜欢计数
     */
    @ApiModelProperty("被点赞数量")
    private Integer likesCount;

}
