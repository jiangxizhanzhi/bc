package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户笔记 SCORE BO
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class UserNoteScoreBo implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("得分")
	private Double score;

}
