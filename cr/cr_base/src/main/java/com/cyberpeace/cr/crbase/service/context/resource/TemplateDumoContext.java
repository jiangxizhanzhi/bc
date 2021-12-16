package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class TemplateDumoContext implements Serializable {

	private JSONObject role_list;

	private Integer people_number;

	private BaseInfoDumpContext base_info;
}
