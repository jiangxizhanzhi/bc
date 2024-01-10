package com.cyberpeace.scene.service.bo.ad;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class AdSceneStartReq {

	private String id;

	private AdSceneContext context;

	private JSONObject scene_config;
}
