package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户笔记得分Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class UserNoteScoreBo implements Serializable {

	/**
	 * 用户编号
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 姓名
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 得分
	 */
	@ApiModelProperty("得分")
	private Double score;

}
