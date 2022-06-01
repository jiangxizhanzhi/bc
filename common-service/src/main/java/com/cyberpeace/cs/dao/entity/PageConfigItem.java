package com.cyberpeace.cs.dao.entity;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class PageConfigItem extends BaseTable {

	private Integer type;

	private String enName;

	private String cnName;

	private String value;

}
