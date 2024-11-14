package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.controller.req.AttackMessageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicStage implements Serializable {

	/**
	 * 阶段名称
	 */
	@ApiModelProperty("阶段名称")
	@NotBlank
	private String name;

	/**
	 * 动效数据
	 */
	@ApiModelProperty("动效数据")
	private List<AttackMessageReq> attackMessageReqList;

}
