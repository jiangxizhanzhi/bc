package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class SceneEventContext implements Serializable {

	private Integer page;

	private Integer pageSize;

	private String sceneId;

	private Boolean flow;

	private Boolean host;

	private AttackScreenContext attack;

	private AttackScreenContext defense;

}
