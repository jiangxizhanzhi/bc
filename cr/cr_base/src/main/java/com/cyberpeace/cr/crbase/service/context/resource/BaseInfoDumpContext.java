package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class BaseInfoDumpContext implements Serializable {

	private String name;

	private String description;

	private Integer category;

	private Integer difficult;

	private Integer duration_time;

	private JSONObject scene_config_data;

	private List<String> pre_skills;

	private List<String> train_skill;
}
