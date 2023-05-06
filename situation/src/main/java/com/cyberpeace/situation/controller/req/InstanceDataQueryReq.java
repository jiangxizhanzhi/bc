package com.cyberpeace.situation.controller.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏数据刷新请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class InstanceDataQueryReq {

	/**
	 * 视角类型
	 */
	@ApiModelProperty(value = "视角类型", required = false)
	private String type;

	/**
	 * 大屏ID
	 */
	@ApiModelProperty(value = "大屏ID", required = true)
	private String dashboardId;

	/**
	 * 特征ID
	 */
	@ApiModelProperty(value = "特征ID", required = true)
	private String featureId;

	/**
	 * 大屏角色
	 */
	@ApiModelProperty(value = "大屏角色", required = true)
	private String role;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	/**
	 * 大屏当前天文时间
	 */
	@ApiModelProperty(value = "大屏当前天文时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date currentTime;

}
