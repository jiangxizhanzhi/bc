package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;

import lombok.Data;

/**
 * 大屏模板echarts数据返回
 *
 * @Author xuxz
 * @Date 18/11/2022 上午10:34
 */
@Data
public class DashboardTemplateEchartsData implements Serializable {

	/**
	 * 列数据
	 */
	private JSONArray columns;

	/**
	 * 行数据
	 */
	private JSONArray rows;
}
