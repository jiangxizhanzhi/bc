package com.cyberpeace.cr.crbase.service.context.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class MissionsStatistics {

	private Integer finishProportion;

	private Integer notFinishProportion;

	private Integer notSubmitProportion;

	private Integer unpublishedProportion;

	@JsonIgnore
	private Integer missionCount;
}
