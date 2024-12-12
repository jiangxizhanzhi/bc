package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评论 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class CommentBo implements Serializable {

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
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("评论内容")
	private String content;

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
	 * 根id
	 *
	 * @see String
	 */
	@ApiModelProperty("评论根编号")
	private String rootId;

	/**
	 * 答id
	 *
	 * @see String
	 */
	@ApiModelProperty("被回复评论编号")
	private String replyId;

	/**
	 * 回复用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("被回复人编号")
	private String replyUsername;

	/**
	 * 答用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("被回复人用户名")
	private String replyUserId;

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

	/**
	 * 答计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("被回复的数量")
	private Integer replyCount;

	/**
	 * 评论 回复
	 *
	 * @see List<CommentBo>
	 */
	@ApiModelProperty("被回复的评论")
	private List<CommentBo> commentReply;

}
