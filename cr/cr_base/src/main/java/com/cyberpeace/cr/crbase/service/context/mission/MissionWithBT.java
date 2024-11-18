package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务模板返回体with训练模板id
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MissionTemplateRes
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionWithBT extends MissionTemplateRes implements Serializable {

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

}
