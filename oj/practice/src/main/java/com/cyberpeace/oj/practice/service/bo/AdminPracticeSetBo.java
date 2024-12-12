package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理实践设置博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AuthTable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class AdminPracticeSetBo extends AuthTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("logo")
	private String logo;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题集类型 0=基础理论 2=实践操作")
	private Integer practiceType;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 创造用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	/**
	 * 实操计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权操作人数量")
	private Integer operationCount;

	/**
	 * 读计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权访问人数量")
	private Integer readCount;

}
