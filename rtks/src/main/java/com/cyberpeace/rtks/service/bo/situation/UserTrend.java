package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.crbase.utils.CrCommonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserTrend implements Serializable {

	@ApiModelProperty(value = "岗位列表")
	List<UserTrendRole> roles = Lists.newArrayList();
	@ApiModelProperty(value = "x轴日期")
	private List<String> xDates = Lists.newArrayList();

	public void addRole(String name, List<Integer> matches) {
		UserTrendRole userTrendRole = new UserTrendRole();
		userTrendRole.setRoleName(name);
		CrCommonUtil.fill(matches, xDates.size(), 0);
		userTrendRole.setYMatch(matches);
		this.roles.add(userTrendRole);
	}
}
