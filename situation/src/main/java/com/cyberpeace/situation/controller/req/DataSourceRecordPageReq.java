package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据源分页请求
 *
 * @Author xuxz
 * @Date 3/11/2022 上午10:30
 */
@Data
public class DataSourceRecordPageReq extends PageReqBase implements Serializable {

	/**
	 * 资源值
	 */
	@ApiModelProperty("资源值")
	private String resourceId;

	/**
	 * 数据源名称
	 */
	@ApiModelProperty("数据源名称")
	private String name;

	/**
	 * 数据源类型
	 */
	@ApiModelProperty("数据源类型")
	private String type;

	/**
	 * 数据源连接地址
	 */
	@ApiModelProperty("数据源连接地址")
	private String connectAddress;

	/**
	 * 数据源连接端口
	 */
	@ApiModelProperty("数据源连接端口")
	private String port;
}
