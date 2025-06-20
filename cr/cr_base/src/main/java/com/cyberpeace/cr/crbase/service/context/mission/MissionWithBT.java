package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板返回体with训练模板id
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionWithBT extends MissionTemplateRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
