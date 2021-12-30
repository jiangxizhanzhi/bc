package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

@Data
public class TrainingCount implements Serializable {

	private Integer completed = 0;

	private Integer uncompleted = 0;
}
