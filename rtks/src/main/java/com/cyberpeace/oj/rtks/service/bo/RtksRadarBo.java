package com.cyberpeace.oj.rtks.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author yinqingrong
 */
@Data
public class RtksRadarBo implements Serializable {

	/**
	 * 层级id
	 */
	private String firstTreeId;

	/**
	 * 层级名称
	 */
	private String treeName;

	/**
	 * 该层级下的能力匹配度百分比。
	 * 10代表10%
	 */
	private String matchPercent;
}
