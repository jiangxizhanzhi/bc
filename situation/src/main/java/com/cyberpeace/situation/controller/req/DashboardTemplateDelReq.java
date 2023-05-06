package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 大屏模板删除请求
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:55
 */
@Data
public class DashboardTemplateDelReq implements Serializable {

	/**
	 * 大屏模板id列表
	 */
	private List<String> dashboardTemplateIdList;
}
