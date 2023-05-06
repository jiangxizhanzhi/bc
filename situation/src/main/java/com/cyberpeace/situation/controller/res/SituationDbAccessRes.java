package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库访问记录返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationDbAccessRes extends SituationComponentsAnimationAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty()
	private String resourceId;

	/**
	 * 数据库名称
	 */
	@ApiModelProperty("数据库名称")
	private String dbName;

	/**
	 * 表名
	 */
	@ApiModelProperty("表名")
	private String tableName;

	/**
	 * 操作类型
	 */
	@ApiModelProperty("操作类型")
	private String operationType;

	/**
	 * 操作时间
	 */
	@ApiModelProperty("操作时间")
	private String creatTime;

	/**
	 * 操作详情
	 */
	@ApiModelProperty("操作详情")
	private String operationDetail;

}
