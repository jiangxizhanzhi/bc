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
 * @date 2024/11/14
 */
@Data
@ApiModel("课程评论信息")
public class CommentRes implements Serializable {

	/**
	 * 头像
	 */
	@ApiModelProperty("头像")
    private String avatar;

	/**
	 * 评论资源编号
	 */
	@ApiModelProperty("评论资源编号")
    private String resourceId;

	/**
	 * 资源编号 课程、习题
	 */
	@ApiModelProperty("资源编号 课程、习题")
    private String businessId;

	/**
	 * 评论主题名称
	 */
	@ApiModelProperty("评论主题名称")
    private String themeName;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
    private String userName;

	/**
	 * 评论内容
	 */
	@ApiModelProperty("评论内容")
    private String comment;

	/**
	 * 评论类型
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
	 * 是否被点赞
	 */
	@ApiModelProperty("是否被点赞")
    private Boolean isLike;

	/**
	 * 被点赞数量
	 */
	@ApiModelProperty("被点赞数量")
    private Integer likesCount;

}
