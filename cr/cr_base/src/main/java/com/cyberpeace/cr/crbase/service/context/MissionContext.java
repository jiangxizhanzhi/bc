package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

@Data
public class MissionContext extends Mission {

	private Integer state;

	private TrainingRecord trainingRecord;

}
