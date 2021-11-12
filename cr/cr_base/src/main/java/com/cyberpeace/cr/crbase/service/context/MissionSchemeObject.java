package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class MissionSchemeObject {

	String stage;

	List<MissionContext> missions;

	List<String> dynamic;
}
