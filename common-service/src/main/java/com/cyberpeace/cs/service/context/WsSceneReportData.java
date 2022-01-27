package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class WsSceneReportData implements Serializable {

	private String type;

	private JSONObject data;
}
