package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 用户统计完成任务和分数
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class UserCounter {

	/**
	 * 得分
	 */
	private Integer score = 0;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 远程任务id列表
	 */
	private List<String> remoteMissionIds = Lists.newArrayList();

}
