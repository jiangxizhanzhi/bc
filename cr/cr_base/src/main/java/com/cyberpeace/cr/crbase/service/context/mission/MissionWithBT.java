package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

@Data
public class MissionWithBT extends MissionTemplateRes implements Serializable {

	private String businessTemplateId;

	private Integer type;

	private Integer score;

}
