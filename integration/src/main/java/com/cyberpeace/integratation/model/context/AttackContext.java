package com.cyberpeace.integratation.model.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttackContext implements Serializable {

	private String sceneId;

	private List<String> ips;
}
