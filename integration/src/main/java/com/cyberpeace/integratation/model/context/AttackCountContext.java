package com.cyberpeace.integratation.model.context;

import lombok.Data;

import java.util.List;

@Data
public class AttackCountContext {

	private String sceneId;

	private List<String> ips;
}
