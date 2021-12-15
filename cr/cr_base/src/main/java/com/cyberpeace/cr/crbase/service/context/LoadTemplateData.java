package com.cyberpeace.cr.crbase.service.context;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class LoadTemplateData {

	private JSONObject templateData;

	private Integer type;

}
