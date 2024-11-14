package com.cyberpeace.cr.singletrain.dao.entity;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import lombok.Data;

/**
 * 单人房名单
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SingleRoomList {

	/**
	 * 业务模板
	 */
	private BusinessTemplate businessTemplate;

	/**
	 * 单个模板
	 */
	private SingleTemplate singleTemplate;

	/**
	 * 单人房
	 */
	private SingleRoom singleRoom;

	/**
	 * 任务计划
	 */
	private String missionsScheme;

}
