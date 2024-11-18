package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 用户统计完成任务和分数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class UserCounter {

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score = 0;

	/**
	 * 角色id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 远程任务id列表
	 *
	 * @see List<String>
	 */
	private List<String> remoteMissionIds = Lists.newArrayList();

}
