package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 用户统计完成任务和分数
 */
@Data
public class UserCounter {

	private Integer score = 0;

	private String roleId;

	private List<String> remoteMissionIds = Lists.newArrayList();

}
