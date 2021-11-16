package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class MissionSchemeObject {

	private String stage;

	private List<MissionContext> missions;

	private List<String> dynamic;
}
