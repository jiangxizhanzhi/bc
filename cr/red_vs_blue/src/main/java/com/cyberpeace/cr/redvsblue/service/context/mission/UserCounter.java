package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class UserCounter {

	private Integer score = 0;

	private String roleId;

	private List<String> remoteMissionIds = Lists.newArrayList();

}
