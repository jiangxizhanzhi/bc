package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学员得分列表响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class StudentScoreListRsp implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "学员id")
	private String userId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "学员姓名")
	private String name;

	/**
	 * 用户头像
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "学员头像")
	private String userAvatar;

	/**
	 * 角色id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 角色名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色类型， 2:团队学员，4：红方，5：蓝方")
	private Integer roleGroup;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "学员评分")
	private Integer score;

	/**
	 * 得分
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否已被评分")
	private Boolean scored;

	/**
	 * 得分时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "评分时间")
	private Date scoreTime;

}
