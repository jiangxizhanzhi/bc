package com.cyberpeace.cs.service.context;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * WS场景报告数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class WsSceneReportData implements Serializable {

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 数据
	 */
	private JSONObject data;
}
