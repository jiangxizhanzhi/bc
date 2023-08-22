package com.cyberpeace.cr.teamtrain.controller.rsp;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamCapabilityInfoRsp implements Serializable {

	/**
	 * 次数
	 */
	private Integer times;

	/**
	 * 得分
	 */
	private Integer score;

}
