package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentBo implements Serializable {

	@ApiModelProperty("头像")
	private String avatar;

	@ApiModelProperty("评论资源编号")
	private String resourceId;

	@ApiModelProperty("资源编号 课程、习题")
	private String businessId;

	@ApiModelProperty("评论主题名称")
	private String themeName;

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("评论内容")
	private String content;

	@ApiModelProperty("评论类型")
	private Integer businessType;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("评论根编号")
	private String rootId;

	@ApiModelProperty("被回复评论编号")
	private String replyId;

	@ApiModelProperty("被回复人编号")
	private String replyUsername;

	@ApiModelProperty("被回复人用户名")
	private String replyUserId;

	@ApiModelProperty("是否被点赞")
	private Boolean isLike;

	@ApiModelProperty("被点赞数量")
	private Integer likesCount;

	@ApiModelProperty("被回复的数量")
	private Integer replyCount;

	@ApiModelProperty("被回复的评论")
	private List<CommentBo> commentReply;

	@ApiModelProperty("最近回帖人")
	private String lastReplyUserName;

	@ApiModelProperty("最近回帖时间")
	private Date lastReplyTime;

}
