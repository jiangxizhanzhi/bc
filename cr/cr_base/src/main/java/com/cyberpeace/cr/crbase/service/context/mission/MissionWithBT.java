package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务模板返回体with训练模板id
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MissionWithBT extends MissionTemplateRes implements Serializable {

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 得分
	 */
	private Integer score;

}
