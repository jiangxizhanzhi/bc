package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class EventDataBo implements Serializable {

	private String name;

	private String description;

	private Integer category;

	private String createUser;

	private List<String> preSkill;

	private List<String> trainSkill;

	private JSONObject sceneConfigData;

	private Integer difficult;

	private Integer durationTime;

	private String logo;

}
