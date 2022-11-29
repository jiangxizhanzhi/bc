package com.cyberpeace.oj.ojbase.service.context;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

@Data
public class AdminNoteListQuery extends PatternBase {

	private String userId;

	private String userName;

	private Integer noteType;

	private Integer businessType;

	private String businessId;

}
