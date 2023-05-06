package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库访问记录请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationDbAccessAddReq implements Serializable {

	/**
	 * 数据库名称
	 */
	@ApiModelProperty(value = "数据库名称")
	private String dbName;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private String operationTime;

	/**
	 * 表名称
	 */
	@ApiModelProperty(value = "表名称")
	private String tableName;

	/**
	 * 操作类型
	 */
	@ApiModelProperty(value = "操作类型")
	private String operationType;

	/**
	 * 操作详情
	 */
	@ApiModelProperty(value = "操作详情")
	private String operationDetail;

}
