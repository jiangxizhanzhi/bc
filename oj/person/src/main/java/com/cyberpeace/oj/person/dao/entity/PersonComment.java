package com.cyberpeace.oj.person.dao.entity;

import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人员评论
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PersonComment extends CommentReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("评论资源编号")
	private String resourceId;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 根业务id
	 */
	@ApiModelProperty("上层资源编号")
	private String rootBusinessId;

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private String createTime;

	/**
	 * 答数量
	 */
	@ApiModelProperty("评论回复数量")
	private Integer replyNum;

	/**
	 * 根注释
	 */
	@ApiModelProperty("被评论名称")
	private String rootComment;
}
