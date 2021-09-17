package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class TestInfo extends BaseTable {

	private String infoId;

	private String infoName;

	private String infoDesc;

	private String logoId;

	private String topoId;

	private String schemeId;

	private String userId;
}
