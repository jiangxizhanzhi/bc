package com.cyberpeace.cr.crbase.service.context.mission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
 * @BelongsPackage: com.cyberpeace.cr.crbase.service.context.mission
 * @Author: hhuang
 * @CreateTime: 2025/2/27 18:28
 * @Description: 任务ws的data部分
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskMissionWsData implements Serializable {

	/**
	 * 代号
	 */
	private Integer code;

	/**
	 * 角色id
	 */
	private String roleResourceId;

	/**
	 * 业务id
	 */
	private String roomId;

	/**
	 * 角色名称
	 */
	private String roleResourceName;

	/**
	 * 任务资源id列表
	 */
	private List<String> missionResourceIds;

}
