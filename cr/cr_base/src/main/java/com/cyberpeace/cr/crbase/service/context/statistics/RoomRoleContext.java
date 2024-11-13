package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练角色模型
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class RoomRoleContext implements Serializable {

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 岗位组
	 */
	private Integer roleGroup;
}
