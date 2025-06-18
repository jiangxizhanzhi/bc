package com.cyberpeace.ai.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("ai_user_profile_report")
public class UserProfileReport extends BaseTable {

	/**
	 * 课程表编号
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("用户画像报告编号")
	private String resourceId;

	/**
	 * 用户ID
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 画像维度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("画像维度")
	private Integer dimension;

	/**
	 * 报告名称
	 *
	 * @see String
	 */
	@ApiModelProperty("报告名称")
	private String name;

	/**
	 * 报告内容
	 *
	 * @see String
	 */
	@ApiModelProperty("报告内容")
	private String content;

}