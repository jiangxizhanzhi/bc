package com.cyberpeace.cr.crbase.model.situation;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.constant.SituationRouteStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势路线Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationRouteBo implements Serializable {

	/**
	 * 节点
	 */
	@ApiModelProperty(value = "节点名称")
	private String node;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "节点状态， 1：未受攻击；2：正被攻击；3：攻击完成")
	private Integer status = SituationRouteStatus.NOT_ATTACKED;
}