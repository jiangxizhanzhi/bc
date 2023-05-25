package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户趋势-单个岗位
 *
 * @author SN
 * @date 2023/05/25
 */
@Data
public class UserTrendRole implements Serializable {

	@ApiModelProperty(value = "岗位名称")
	private String roleName;

	@ApiModelProperty(value = "满足度趋势")
	private List<Integer> yMatch = Lists.newArrayList();
}
