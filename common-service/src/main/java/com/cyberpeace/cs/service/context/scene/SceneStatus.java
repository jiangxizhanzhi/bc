package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class SceneStatus implements Serializable {

	private Integer event;

	private Integer eventStatus;
}
