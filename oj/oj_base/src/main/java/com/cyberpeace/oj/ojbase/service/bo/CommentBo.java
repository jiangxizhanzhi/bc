package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评论 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CommentBo implements Serializable {

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
	 * 内容
	 */
	@ApiModelProperty("评论内容")
	private String content;

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
	 * 根id
	 */
	@ApiModelProperty("评论根编号")
	private String rootId;

	/**
	 * 答id
	 */
	@ApiModelProperty("被回复评论编号")
	private String replyId;

	/**
	 * 回复用户名
	 */
	@ApiModelProperty("被回复人编号")
	private String replyUsername;

	/**
	 * 答用户id
	 */
	@ApiModelProperty("被回复人用户名")
	private String replyUserId;

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

	/**
	 * 答计数
	 */
	@ApiModelProperty("被回复的数量")
	private Integer replyCount;

	/**
	 * 评论 回复
	 */
	@ApiModelProperty("被回复的评论")
	private List<CommentBo> commentReply;

}
