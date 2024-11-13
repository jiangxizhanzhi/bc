package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理实践设置博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class AdminPracticeSetBo extends AuthTable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 图标
	 */
	@ApiModelProperty("logo")
	private String logo;

	/**
	 * 执业类型
	 */
	@ApiModelProperty("习题集类型 0=基础理论 2=实践操作")
	private Integer practiceType;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 创造用户名称
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 实践计数
	 */
	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	/**
	 * 实操计数
	 */
	@ApiModelProperty("授权操作人数量")
	private Integer operationCount;

	/**
	 * 读计数
	 */
	@ApiModelProperty("授权访问人数量")
	private Integer readCount;

}
