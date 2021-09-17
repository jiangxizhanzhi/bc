package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 附件表
 */
@Data
public class Appendix extends BaseTable {

	/** 附件编号*/
	private String appendixId;

	/** 附件名*/
	private String appendixName;

	/** 附件路径*/
	private String appendixUrl;

}
