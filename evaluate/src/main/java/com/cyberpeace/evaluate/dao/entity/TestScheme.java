package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TestScheme extends BaseTable {

	private String testSchemeId;

	private String schemeName;

	private String schemeDesc;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String logoId;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String content;
}
