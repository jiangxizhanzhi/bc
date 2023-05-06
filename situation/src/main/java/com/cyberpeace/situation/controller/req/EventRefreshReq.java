package com.cyberpeace.situation.controller.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件刷新请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EventRefreshReq {

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
	 * 分靶场ID
	 */
	@ApiModelProperty(value = "分靶场ID", required = false)
	private String subRangeId;

	/**
	 * 模式
	 */
	@ApiModelProperty(value = "模式 1-实时 2-回放", required = true)
	private Integer mode;

	/**
	 * 大屏角色
	 */
	@ApiModelProperty(value = "大屏角色", required = true)
	private String role;

	/**
	 * 大屏当前天文时间
	 */
	@ApiModelProperty(value = "大屏当前天文时间", required = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date currentTime;

}
