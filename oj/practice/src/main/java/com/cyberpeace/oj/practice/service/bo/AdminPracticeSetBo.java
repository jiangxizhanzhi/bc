package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理练习设置Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminPracticeSetBo extends AuthTable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * logo
	 */
	@ApiModelProperty("logo")
	private String logo;

	/**
	 * 习题集类型 0=基础理论 2=实践操作
	 */
	@ApiModelProperty("习题集类型 0=基础理论 2=实践操作")
	private Integer practiceType;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 创建人名称
	 */
	@ApiModelProperty("创建人名称")
	private String createUserName;

	/**
	 * 习题数量
	 */
	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	/**
	 * 授权操作人数量
	 */
	@ApiModelProperty("授权操作人数量")
	private Integer operationCount;

	/**
	 * 授权访问人数量
	 */
	@ApiModelProperty("授权访问人数量")
	private Integer readCount;

}
