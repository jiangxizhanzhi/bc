package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Map;

import lombok.Data;

@Data
public class MissionReport {

	private Map<String, Integer> missionsCategory;

	private MissionsStatistics missionsStatistics;
}
