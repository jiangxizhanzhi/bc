package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

@Data
public class NodeListReq implements Serializable {

	private String subRangeId;

	private String taskId;

	private String subjectId;

	private String schemeId;

	private String platformId;
}
