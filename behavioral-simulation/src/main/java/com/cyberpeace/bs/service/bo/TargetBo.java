package com.cyberpeace.bs.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 靶标数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class TargetBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "设备id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * ip地址
	 */
	@ApiModelProperty(value = "IP")
	private String ip;

	/**
	 * 监控url
	 */
	@ApiModelProperty(value = "监控链接")
	private String monitorUrl;
}
