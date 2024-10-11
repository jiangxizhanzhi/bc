package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组任务下发进度
 *
 * @author SN
 */
@Data
public class GroupTaskReleaseProgress extends ReleaseProgress implements Serializable {

	@ApiModelProperty(value = "各角色下发进度")
	private List<RoleTaskReleaseProgress> role = Lists.newArrayList();
}
