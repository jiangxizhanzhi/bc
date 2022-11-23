package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

@Data
public class LcVariableModifyReq implements Serializable {

	/**
	 * 变量列表
	 */
	private List<LcVariableBase> entityList = Lists.newArrayList();
}
