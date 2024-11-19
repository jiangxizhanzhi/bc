package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.controller.req.AttackMessageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态阶段
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DynamicStage implements Serializable {

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	@ApiModelProperty("阶段名称")
	@NotBlank
	private String name;

	/**
	 * 动效数据
	 *
	 * @see List<AttackMessageReq>
	 */
	@ApiModelProperty("动效数据")
	private List<AttackMessageReq> attackMessageReqList;

}
