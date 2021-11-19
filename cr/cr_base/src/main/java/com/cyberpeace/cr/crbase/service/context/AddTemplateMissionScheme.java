package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class AddTemplateMissionScheme {

	private String stage;

	private List<AddTemplateMission> missions;

	private List<String> dynamic;
}
