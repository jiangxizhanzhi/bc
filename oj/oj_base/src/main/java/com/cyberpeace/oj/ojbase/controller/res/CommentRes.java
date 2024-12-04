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
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("课程评论信息")
public class CommentRes implements Serializable {

    /**
     * 化身
     *
     * @see String
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("评论资源编号")
    private String resourceId;

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("资源编号 课程、习题")
    private String businessId;

    /**
     * 主题名称
     *
     * @see String
     */
    @ApiModelProperty("评论主题名称")
    private String themeName;

    /**
     * 用户名称
     *
     * @see String
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 评论
     *
     * @see String
     */
    @ApiModelProperty("评论内容")
    private String comment;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("评论类型")
    private Integer businessType;

    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 父母id
     *
     * @see String
     */
    private String parentId;

    /**
     * 就像
     *
     * @see Boolean
     */
    @ApiModelProperty("是否被点赞")
    private Boolean isLike;

    /**
     * 喜欢计数
     *
     * @see Integer
     */
    @ApiModelProperty("被点赞数量")
    private Integer likesCount;

}
