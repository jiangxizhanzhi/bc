package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务模板返回体with训练模板id
 */
@Data
public class MissionWithBT extends MissionTemplateRes implements Serializable {

	private String businessTemplateId;

	private Integer type;

	private Integer score;

}
