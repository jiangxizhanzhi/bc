package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * 大屏组件资源请求
 *
 * @Author xuxz
 * @Date 28/11/2022 上午11:15
 */
@Data
public class DashboardComponentsResourceReq implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 自定义参数
	 */
	private String customParams;
}
