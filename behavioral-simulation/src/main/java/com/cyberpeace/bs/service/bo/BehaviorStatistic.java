package com.cyberpeace.bs.service.bo;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.Data;

@Data
public class BehaviorStatistic implements Serializable {

	private LocalTime startTime;

	private LocalTime endTime;

	private Integer behaviorCount = 0;
}
