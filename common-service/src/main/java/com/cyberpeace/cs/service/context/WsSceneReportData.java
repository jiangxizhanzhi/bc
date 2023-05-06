package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * ws场景报告数据
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class WsSceneReportData implements Serializable {

	private String type;

	private JSONObject data;
}
