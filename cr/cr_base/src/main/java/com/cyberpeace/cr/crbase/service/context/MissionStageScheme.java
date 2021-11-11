package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class MissionStageScheme {

	String stage;

	List<String> missions;

	List<String> dynamic;
}
