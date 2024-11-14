package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评论Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class CommentBo implements Serializable {

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
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 评论内容
	 */
	@ApiModelProperty("评论内容")
	private String content;

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
	 * 评论根编号
	 */
	@ApiModelProperty("评论根编号")
	private String rootId;

	/**
	 * 被回复评论编号
	 */
	@ApiModelProperty("被回复评论编号")
	private String replyId;

	/**
	 * 被回复人编号
	 */
	@ApiModelProperty("被回复人编号")
	private String replyUsername;

	/**
	 * 被回复人用户名
	 */
	@ApiModelProperty("被回复人用户名")
	private String replyUserId;

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

	/**
	 * 被回复的数量
	 */
	@ApiModelProperty("被回复的数量")
	private Integer replyCount;

	/**
	 * 被回复的评论
	 */
	@ApiModelProperty("被回复的评论")
	private List<CommentBo> commentReply;

}
