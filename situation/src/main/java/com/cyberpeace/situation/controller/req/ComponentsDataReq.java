package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击消息请求
 *
 * @Author xuxz
 * @Date 16/11/2022 下午3:09
 */
@Data
public class ComponentsDataReq implements Serializable {

	/**
	 * 数据源配置
	 */
	@ApiModelProperty("数据源配置")
	private String dataSourceComponent;

	/**
	 * 静态数据源
	 */
	@ApiModelProperty("静态数据源")
	private String jsonString;

	/**
	 * 维度
	 */
	@ApiModelProperty("维度")
	private String dimension;

	/**
	 * 度量
	 */
	@ApiModelProperty("度量")
	private String measure;

	/**
	 * 分组
	 */
	@ApiModelProperty("分组")
	private String groups;

	/**
	 * 自定义参数列表
	 */
	@ApiModelProperty("自定义参数列表")
	private String customParams;

	/**
	 * 常量参参数
	 */
	@ApiModelProperty("常量参参数")
	private InstanceDataQueryReq instanceDataQueryReq;
}
