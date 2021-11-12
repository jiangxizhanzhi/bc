package com.cyberpeace.cr.teamtrain.service.context;

import java.util.Map;

import lombok.Data;

@Data
public class MissionReport {

	Map<String, Integer> missionsCategory;

	MissionsStatistics missionsStatistics;
}
