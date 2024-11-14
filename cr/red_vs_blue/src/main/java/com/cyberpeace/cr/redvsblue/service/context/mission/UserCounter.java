package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 用户统计完成任务和分数
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class UserCounter {

	/**
	 * 得分
	 */
	private Integer score = 0;

	/**
	 * 岗位id
	 */
	private String roleId;

	/**
	 * 远程任务id列表
	 */
	private List<String> remoteMissionIds = Lists.newArrayList();

}
