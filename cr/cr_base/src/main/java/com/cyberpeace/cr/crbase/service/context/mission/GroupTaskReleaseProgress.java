package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 组任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Getter
@Setter
public class GroupTaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "各角色下发进度")
	private List<RoleTaskReleaseProgress> role = Lists.newArrayList();
}
