package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Map;

import lombok.Data;

@Data
public class MissionReport {

	private Map<String, Integer> missionsCategory;

	private MissionsStatistics missionsStatistics;
}
