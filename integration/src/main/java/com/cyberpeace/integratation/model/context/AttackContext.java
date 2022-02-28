package com.cyberpeace.integratation.model.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttackContext implements Serializable {

	private String sceneId;

//	private List<String> ips;
	//get请求中 用逗号分割，代表数组
	private String ips;
}
