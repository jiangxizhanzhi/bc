package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户笔记 SCORE BO
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class UserNoteScoreBo implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 得分
	 */
	@ApiModelProperty("得分")
	private Double score;

}
