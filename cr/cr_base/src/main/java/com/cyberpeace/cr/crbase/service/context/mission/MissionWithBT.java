package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * 任务模板返回体with训练模板id
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionWithBT extends MissionTemplateRes implements Serializable {

	private String businessTemplateId;

	private Integer type;

	private Integer score;

}
