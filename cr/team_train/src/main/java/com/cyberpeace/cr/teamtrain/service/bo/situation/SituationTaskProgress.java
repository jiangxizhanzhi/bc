package com.cyberpeace.cr.teamtrain.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationTaskProgress implements Serializable {

	@ApiModelProperty(value = "总任务数")
	private Integer total;

	@ApiModelProperty(value = "已下发")
	private Integer released;

    @ApiModelProperty(value = "未下发")
    private Integer unreleased;
}
