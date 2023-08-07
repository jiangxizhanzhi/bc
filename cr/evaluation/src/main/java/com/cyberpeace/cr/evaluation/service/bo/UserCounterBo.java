package com.cyberpeace.cr.evaluation.service.bo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 用户统计完成任务和分数
 */
@Data
public class UserCounterBo {

	private Integer score = 0;

	private String roleId;

	private List<String> remoteMissionIds = Lists.newArrayList();

}
