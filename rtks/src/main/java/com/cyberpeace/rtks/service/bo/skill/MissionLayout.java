package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionLayout implements Serializable {

	@ApiModelProperty("岗位id")
	private String roleId;

	@ApiModelProperty("任务id列表")
	private List<String> missionIds;
}
