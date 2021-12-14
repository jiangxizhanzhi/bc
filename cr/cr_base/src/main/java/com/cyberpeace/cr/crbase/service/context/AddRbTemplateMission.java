package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddRbTemplateMission implements Serializable {

	private String resourceId;

	private String title;

	private String description;

	private String writeUp;
}
