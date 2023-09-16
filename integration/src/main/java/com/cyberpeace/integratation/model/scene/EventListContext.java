package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventListContext implements Serializable {

	private EventScreenContext flow;

	private EventScreenContext host;

	private Integer page;

	private Integer pageSize;

	private String sceneId;
}
