package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class SceneDumpContext implements Serializable {

	private String id;

	private String envPath;

	private String imagePath;
}