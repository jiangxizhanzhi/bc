package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcVariableRspBo extends LcVariableBase implements Serializable {

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
