package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 训练角色模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class RoomRoleContext implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 岗位群
	 */
	private Integer roleGroup;
}
