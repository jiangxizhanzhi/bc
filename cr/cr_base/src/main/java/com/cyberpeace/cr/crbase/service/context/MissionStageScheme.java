package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class MissionStageScheme {

	private String stage;

	private List<String> missions;

	private List<String> dynamic;
}
